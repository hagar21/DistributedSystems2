// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: scheme.proto

package generated;

public interface InsertCustomerRequestToDbOrBuilder extends
    // @@protoc_insertion_point(interface_extends:InsertCustomerRequestToDb)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.CustomerRequest customerReq = 1;</code>
   * @return Whether the customerReq field is set.
   */
  boolean hasCustomerReq();
  /**
   * <code>.CustomerRequest customerReq = 1;</code>
   * @return The customerReq.
   */
  generated.CustomerRequest getCustomerReq();
  /**
   * <code>.CustomerRequest customerReq = 1;</code>
   */
  generated.CustomerRequestOrBuilder getCustomerReqOrBuilder();

  /**
   * <code>int32 rideId = 2;</code>
   * @return The rideId.
   */
  int getRideId();

  /**
   * <code>int32 vacancies = 3;</code>
   * @return The vacancies.
   */
  int getVacancies();
}