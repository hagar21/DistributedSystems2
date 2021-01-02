// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: city.proto

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

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\ncity.proto\"\230\001\n\004Ride\022\n\n\002id\030\001 \001(\005\022\021\n\tfir" +
      "stName\030\002 \001(\t\022\020\n\010lastName\030\003 \001(\t\022\020\n\010phoneN" +
      "um\030\004 \001(\005\022\017\n\007srcCity\030\005 \001(\t\022\017\n\007dstCity\030\006 \001" +
      "(\t\022\014\n\004date\030\007 \001(\t\022\021\n\tvacancies\030\010 \001(\005\022\n\n\002p" +
      "d\030\t \001(\005\"\224\001\n\017CustomerRequest\022\n\n\002id\030\001 \001(\005\022" +
      "\021\n\tfirstName\030\002 \001(\t\022\020\n\010lastName\030\003 \001(\t\022\020\n\010" +
      "phoneNum\030\004 \001(\005\022\017\n\007srcCity\030\005 \001(\t\022\017\n\007dstCi" +
      "ty\030\006 \001(\t\022\014\n\004date\030\007 \001(\t\022\016\n\006rideId\030\010 \001(\005\"6" +
      "\n\004Rout\022\014\n\004date\030\001 \001(\t\022\017\n\007srcCity\030\002 \001(\t\022\017\n" +
      "\007dstCity\030\003 \001(\t\"\033\n\006Result\022\021\n\tisSuccess\030\001 " +
      "\001(\0102\216\002\n\013UberService\022\034\n\010PostRide\022\005.Ride\032\007" +
      ".Result\"\000\0222\n\023PostCustomerRequest\022\020.Custo" +
      "merRequest\032\007.Result\"\000\0228\n\027PostPathPlannin" +
      "gRequest\022\020.CustomerRequest\032\007.Result\"\000(\001\022" +
      "$\n\020GetExistingRides\022\005.Rout\032\005.Ride\"\0000\001\022)\n" +
      "\nUpdateRide\022\020.CustomerRequest\032\007.Result\"\000" +
      "\022\"\n\016InsertRideToDb\022\005.Ride\032\007.Result\"\000B\036\n\t" +
      "generatedB\tCityProtoP\001\242\002\003RTGb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_Ride_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Ride_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Ride_descriptor,
        new java.lang.String[] { "Id", "FirstName", "LastName", "PhoneNum", "SrcCity", "DstCity", "Date", "Vacancies", "Pd", });
    internal_static_CustomerRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_CustomerRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CustomerRequest_descriptor,
        new java.lang.String[] { "Id", "FirstName", "LastName", "PhoneNum", "SrcCity", "DstCity", "Date", "RideId", });
    internal_static_Rout_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_Rout_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Rout_descriptor,
        new java.lang.String[] { "Date", "SrcCity", "DstCity", });
    internal_static_Result_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_Result_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Result_descriptor,
        new java.lang.String[] { "IsSuccess", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
