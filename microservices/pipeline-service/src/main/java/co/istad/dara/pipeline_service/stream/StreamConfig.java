package co.istad.dara.pipeline_service.stream;


//import co.istad.dara.pipeline_service.stream.avro.DebeziumMessage;
//import co.istad.dara.pipeline_service.stream.avro.ProductAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StreamConfig {
    private final ObjectMapper objectMapper;

    // Supplier for producing message into kafka topic
    // Function for processing message and send to destination kafka topic
    // Consumer for consuming message from kafka topic

    @Bean
    public Consumer<Product> processProduct() {
        return product -> {
            System.out.println("obj product: " + product.getCode());
            System.out.println("obj product: " + product.getQty());
        };
    }

    @Bean
    public Function<Product,Product> processProductDetail(){
        return product -> {
            System.out.println("old product: " + product.getCode());
            System.out.println("old product: " + product.getQty());

            // Processing change data
            product.setCode("Dara's code: " + product.getCode());

            // Return process data
            return product;
        };
    }


    // A simple processor: Takes a string, makes it uppercase, and sends it on
    @Bean
    public Consumer<String> processMessage() {
        return input -> {
            System.out.println("Processing: " + input);
        };
    }

    // Consume from debezium
    @Bean
    public Consumer<GenericRecord> processDebeziumProduct() {
        return record -> {
            try {
                System.out.println("=== Debezium Event (Avro) ===");

                // Extract operation type
                String operation = record.get("op").toString();
                System.out.println("Operation: " + operation);

                // Extract source metadata
                GenericRecord source = (GenericRecord) record.get("source");
                if (source != null) {
                    System.out.println("Database: " + source.get("db"));
                    System.out.println("Table: " + source.get("table"));
                }

                // Handle different operations
                switch (operation) {
                    case "c":  // CREATE
                    case "r":  // READ (initial snapshot)
                        GenericRecord afterCreate = (GenericRecord) record.get("after");
                        System.out.println("New Product: " + extractProduct(afterCreate));
                        handleNewProduct(afterCreate);
                        break;

                    case "u":  // UPDATE
                        GenericRecord before = (GenericRecord) record.get("before");
                        GenericRecord after = (GenericRecord) record.get("after");
                        System.out.println("Before: " + extractProduct(before));
                        System.out.println("After: " + extractProduct(after));
                        handleUpdateProduct(before, after);
                        break;

                    case "d":  // DELETE
                        GenericRecord beforeDelete = (GenericRecord) record.get("before");
                        System.out.println("Deleted: " + extractProduct(beforeDelete));
                        handleDeleteProduct(beforeDelete);
                        break;

                    default:
                        System.out.println("Unknown operation: " + operation);
                }

            } catch (Exception e) {
                System.err.println("Error processing Debezium message: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

    @Bean
    public Function<GenericRecord, Product> processDebeziumProductRobust() {
        return record -> {
            try {
                log.info("=== RECEIVED DEBEZIUM EVENT (Robust Mapping) ===");

                String operation = record.get("op").toString();
                log.info("Operation: {}", operation);

                if ("d".equals(operation)) {
                    log.info("Skipping DELETE operation");
                    return null;
                }

                GenericRecord after = (GenericRecord) record.get("after");
                if (after == null) {
                    log.warn("No 'after' data found");
                    return null;
                }

                // Extract fields directly from GenericRecord
                String code = extractString(after.get("code"));
                Integer qty = extractInteger(after.get("qty"));

                log.info("Extracted: code={}, qty={}", code, qty);

                // Create Product
                Product product = new Product();
                product.setCode("ISTAD-" + code);
                product.setQty(qty);

                log.info("Transformed product: {}", product);

                return product;

            } catch (Exception e) {
                log.error("Error transforming Debezium record", e);
                return null;
            }
        };
    }
    private String extractString(Object value) {
        if (value == null) return null;

        // Handle Avro union types
        if (value instanceof GenericRecord) {
            GenericRecord record = (GenericRecord) value;
            if (record.hasField("string")) {
                return record.get("string").toString();
            }
        }

        return value.toString();
    }

    private Integer extractInteger(Object value) {
        if (value == null) return null;

        // Handle Avro union types
        if (value instanceof GenericRecord) {
            GenericRecord record = (GenericRecord) value;
            if (record.hasField("int")) {
                return (Integer) record.get("int");
            }
            if (record.hasField("long")) {
                return ((Long) record.get("long")).intValue();
            }
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        return Integer.parseInt(value.toString());
    }



    private void handleNewProduct(GenericRecord product) {
        if (product == null) return;

        System.out.println("Processing new product:");
        System.out.println("  ID: " + extractValue(product.get("id")));
        System.out.println("  Code: " + extractValue(product.get("code")));
        System.out.println("  Qty: " + extractValue(product.get("qty")));
    }

    private void handleUpdateProduct(GenericRecord before, GenericRecord after) {
        if (before == null || after == null) return;

        System.out.println("Processing product update:");
        System.out.println("  Old Code: " + extractValue(before.get("code")) +
                " -> New Code: " + extractValue(after.get("code")));
        System.out.println("  Old Qty: " + extractValue(before.get("qty")) +
                " -> New Qty: " + extractValue(after.get("qty")));
    }

    private void handleDeleteProduct(GenericRecord product) {
        if (product == null) return;

        System.out.println("Processing product deletion:");
        System.out.println("  Deleted ID: " + extractValue(product.get("id")));
        System.out.println("  Deleted Code: " + extractValue(product.get("code")));
    }

    // Helper method to extract Avro values (handles union types)
    private Object extractValue(Object value) {
        if (value instanceof GenericRecord) {
            GenericRecord record = (GenericRecord) value;
            // Try common Avro union field names
            if (record.hasField("string")) return record.get("string");
            if (record.hasField("int")) return record.get("int");
            if (record.hasField("long")) return record.get("long");
        }
        return value;
    }

    // Helper to create readable product string
    private String extractProduct(GenericRecord product) {
        if (product == null) return "null";

        return String.format("Product{id=%s, code=%s, qty=%s}",
                extractValue(product.get("id")),
                extractValue(product.get("code")),
                extractValue(product.get("qty"))
        );
    }

}

