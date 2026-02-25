//package co.istad.dara.pipeline_service.stream.domain;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
//
//@Data
//public class Source {
//    private String version;
//    private String connector;
//    private String name;
//
//    @JsonProperty("ts_ms")
//    private Long tsMs;
//
//    // This can be either a string or an object with a "string" field
//    private Object snapshot;  // Change from String to Object
//
//    private String db;
//
//    @JsonProperty("ts_us")
//    private Long tsUs;
//
//    @JsonProperty("ts_ns")
//    private Long tsNs;
//
//    private String schema;
//    private String table;
//
//    @JsonProperty("txId")
//    private String txId;
//
//    private String scn;
//
//    @JsonProperty("commit_scn")
//    private String commitScn;
//
//    @JsonProperty("rs_id")
//    private String rsId;
//
//    private Long ssn;
//
//    @JsonProperty("redo_thread")
//    private Integer redoThread;
//
//    @JsonProperty("user_name")
//    private String userName;
//
//    @JsonProperty("row_id")
//    private String rowId;
//
//    @JsonProperty("commit_ts_ms")
//    private Long commitTsMs;
//
//    @JsonProperty("start_scn")
//    private String startScn;
//
//    @JsonProperty("start_ts_ms")
//    private Long startTsMs;
//
//    @JsonProperty("txSeq")
//    private Long txSeq;
//
//    // Helper method to get snapshot value as string
//    public String getSnapshotAsString() {
//        if (snapshot == null) {
//            return null;
//        }
//        if (snapshot instanceof String) {
//            return (String) snapshot;
//        }
//        // If it's a map/object, try to get the "string" field
//        if (snapshot instanceof java.util.Map) {
//            Object stringValue = ((java.util.Map<?, ?>) snapshot).get("string");
//            return stringValue != null ? stringValue.toString() : null;
//        }
//        return snapshot.toString();
//    }
//}