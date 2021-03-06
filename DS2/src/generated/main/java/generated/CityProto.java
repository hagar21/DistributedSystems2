// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: scheme.proto

package generated;

public final class CityProto {
  private CityProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Ride_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Ride_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CustomerRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CustomerRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Rout_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Rout_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Result_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Result_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CityRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CityRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CityRevertRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CityRevertRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014scheme.proto\032\033google/protobuf/empty.pr" +
      "oto\"\332\001\n\004Ride\022\n\n\002id\030\001 \001(\t\022\021\n\tfirstName\030\002 " +
      "\001(\t\022\020\n\010lastName\030\003 \001(\t\022\020\n\010phoneNum\030\004 \001(\t\022" +
      "\017\n\007srcCity\030\005 \001(\t\022\017\n\007dstCity\030\006 \001(\t\022\014\n\004dat" +
      "e\030\007 \001(\t\022\025\n\rofferedPlaces\030\010 \001(\005\022\023\n\013takenP" +
      "laces\030\t \001(\005\022\n\n\002pd\030\n \001(\005\022\021\n\tcustomers\030\013 \003" +
      "(\t\022\024\n\014sentByLeader\030\014 \001(\010\"s\n\017CustomerRequ" +
      "est\022\n\n\002id\030\001 \001(\t\022\014\n\004name\030\002 \001(\t\022\014\n\004path\030\003 " +
      "\003(\t\022\014\n\004date\030\004 \001(\t\022\024\n\005rides\030\005 \003(\0132\005.Ride\022" +
      "\024\n\014sentByLeader\030\006 \001(\010\"D\n\004Rout\022\014\n\004name\030\001 " +
      "\001(\t\022\014\n\004date\030\002 \001(\t\022\017\n\007srcCity\030\003 \001(\t\022\017\n\007ds" +
      "tCity\030\004 \001(\t\"\033\n\006Result\022\021\n\tisSuccess\030\001 \001(\010" +
      "\"8\n\013CityRequest\022\024\n\014destCityName\030\001 \001(\t\022\023\n" +
      "\004rout\030\002 \001(\0132\005.Rout\">\n\021CityRevertRequest\022" +
      "\024\n\014destCityName\030\001 \001(\t\022\023\n\004ride\030\002 \001(\0132\005.Ri" +
      "de2\235\003\n\013UberService\022\034\n\010PostRide\022\005.Ride\032\007." +
      "Result\"\000\0222\n\023PostCustomerRequest\022\020.Custom" +
      "erRequest\032\007.Result\"\000\0226\n\027PostPathPlanning" +
      "Request\022\020.CustomerRequest\032\005.Ride\"\0000\001\022\035\n\013" +
      "ReserveRide\022\005.Rout\032\005.Ride\"\000\022\036\n\014RevertCom" +
      "mit\022\005.Ride\032\007.Result\022-\n\010Snapshot\022\026.google" +
      ".protobuf.Empty\032\007.Result\"\000\022(\n\017CityReques" +
      "tRide\022\014.CityRequest\032\005.Ride\"\000\0226\n\025CityReve" +
      "rtRequestRide\022\022.CityRevertRequest\032\007.Resu" +
      "lt\"\000\0224\n\025DeleteCustomerRequest\022\020.Customer" +
      "Request\032\007.Result\"\000B\036\n\tgeneratedB\tCityPro" +
      "toP\001\242\002\003RTGb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.EmptyProto.getDescriptor(),
        });
    internal_static_Ride_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Ride_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Ride_descriptor,
        new java.lang.String[] { "Id", "FirstName", "LastName", "PhoneNum", "SrcCity", "DstCity", "Date", "OfferedPlaces", "TakenPlaces", "Pd", "Customers", "SentByLeader", });
    internal_static_CustomerRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_CustomerRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CustomerRequest_descriptor,
        new java.lang.String[] { "Id", "Name", "Path", "Date", "Rides", "SentByLeader", });
    internal_static_Rout_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_Rout_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Rout_descriptor,
        new java.lang.String[] { "Name", "Date", "SrcCity", "DstCity", });
    internal_static_Result_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_Result_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Result_descriptor,
        new java.lang.String[] { "IsSuccess", });
    internal_static_CityRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_CityRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CityRequest_descriptor,
        new java.lang.String[] { "DestCityName", "Rout", });
    internal_static_CityRevertRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_CityRevertRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CityRevertRequest_descriptor,
        new java.lang.String[] { "DestCityName", "Ride", });
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
