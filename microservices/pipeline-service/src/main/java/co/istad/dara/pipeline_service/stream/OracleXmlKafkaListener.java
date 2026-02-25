//package co.istad.dara.pipeline_service.stream;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.avro.generic.GenericRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class OracleXmlKafkaListener {
//
//    @KafkaListener(
//            topics = "ITP.CORE_BANKING.RECORD_XML",
//            groupId = "oracle-xml-consumer-group"
//    )
//    public void listenOracleXmlRecord(ConsumerRecord<GenericRecord, GenericRecord> record) {
//        try {
//            GenericRecord value = record.value();
//
//            // Extract operation
//            String operation = value.get("op").toString();
//            log.info("=== CDC Event Received ===");
//            log.info("Operation: {}", operation);
//
//            // Extract source information
//            GenericRecord source = (GenericRecord) value.get("source");
//            if (source != null) {
//                log.info("Database: {}", source.get("db"));
//                log.info("Schema: {}", source.get("schema"));
//                log.info("Table: {}", source.get("table"));
//                log.info("SCN: {}", source.get("scn"));
//            }
//
//            // Handle different operations
//            switch (operation) {
//                case "r": // Read (snapshot)
//                case "c": // Create
//                    handleCreateOrRead(value);
//                    break;
//                case "u": // Update
//                    handleUpdate(value);
//                    break;
//                case "d": // Delete
//                    handleDelete(value);
//                    break;
//                default:
//                    log.warn("Unknown operation: {}", operation);
//            }
//
//            log.info("=== End of Event ===\n");
//
//        } catch (Exception e) {
//            log.error("Error processing message", e);
//            e.printStackTrace();
//        }
//    }
//
//    private void handleCreateOrRead(GenericRecord value) {
//        Object afterObj = value.get("after");
//        if (afterObj != null && afterObj instanceof GenericRecord) {
//            GenericRecord after = (GenericRecord) afterObj;
//
//            String recId = after.get("RECID").toString();
//            String xmlData = after.get("XMLDATA").toString();
//
//            System.out.println("✅ CREATE/READ Event");
//            System.out.println("RECID: " + recId);
//            log.info("✅ CREATE/READ Event");
//            log.info("   RECID: {}", recId);
//
//            parseAndLogXmlData(xmlData);
//        }
//    }
//
//    private void handleUpdate(GenericRecord value) {
//        log.info("📝 UPDATE Event");
//        System.out.println("===UPDATE Event===");
//
//        // Before state
//        Object beforeObj = value.get("before");
//        if (beforeObj != null && beforeObj instanceof GenericRecord) {
//            GenericRecord before = (GenericRecord) beforeObj;
//            String beforeRecId = before.get("RECID").toString();
//            Object beforeXmlData = before.get("XMLDATA");
//
//            log.info("   BEFORE - RECID: {}", beforeRecId);
//            System.out.println("   BEFORE - RECID: " + beforeRecId);
//            if (beforeXmlData != null && !beforeXmlData.toString().equals("__debezium_unavailable_value")) {
//                log.info("   BEFORE - XML: {}", beforeXmlData.toString().substring(0, Math.min(50, beforeXmlData.toString().length())) + "...");
//            }
//        }
//
//        // After state
//        Object afterObj = value.get("after");
//        if (afterObj != null && afterObj instanceof GenericRecord) {
//            GenericRecord after = (GenericRecord) afterObj;
//
//            String afterRecId = after.get("RECID").toString();
//            String afterXmlData = after.get("XMLDATA").toString();
//
//            log.info("   AFTER - RECID: {}", afterRecId);
//            System.out.println("   AFTER - RECID: " + afterRecId);
//            parseAndLogXmlData(afterXmlData);
//        }
//    }
//
//    private void handleDelete(GenericRecord value) {
//        log.info("🗑️ DELETE Event");
//
//        Object beforeObj = value.get("before");
//        if (beforeObj != null && beforeObj instanceof GenericRecord) {
//            GenericRecord before = (GenericRecord) beforeObj;
//            String recId = before.get("RECID").toString();
//            System.out.println("Deleted RECID: " + recId);
//            log.info("   Deleted RECID: {}", recId);
//        }
//    }
//
//    private void parseAndLogXmlData(String xmlData) {
//        try {
//            // Check for unavailable value
//            if ("__debezium_unavailable_value".equals(xmlData)) {
//                log.warn("   ⚠️ XML data is unavailable (LOB not captured in BEFORE state)");
//                return;
//            }
//
//            // Simple XML parsing
//            if (xmlData.contains("<name>") && xmlData.contains("<role>")) {
//                String name = xmlData.substring(
//                        xmlData.indexOf("<name>") + 6,
//                        xmlData.indexOf("</name>")
//                ).trim();
//
//                String role = xmlData.substring(
//                        xmlData.indexOf("<role>") + 6,
//                        xmlData.indexOf("</role>")
//                ).trim();
//
//                System.out.println("Parsed XML: ");
//                System.out.println("Name: "+ name);
//                System.out.println("Role: "+ role);
//                log.info("   📄 Parsed XML:");
//                log.info("      Name: {}", name);
//                log.info("      Role: {}", role);
//            } else {
//                log.info("   Raw XML (first 100 chars): {}",
//                        xmlData.substring(0, Math.min(100, xmlData.length())));
//            }
//        } catch (Exception e) {
//            log.error("   Error parsing XML", e);
//        }
//    }
//}