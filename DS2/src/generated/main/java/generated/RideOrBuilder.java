// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: scheme.proto

package generated;

public interface RideOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Ride)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string id = 1;</code>
   * @return The id.
   */
  java.lang.String getId();
  /**
   * <code>string id = 1;</code>
   * @return The bytes for id.
   */
  com.google.protobuf.ByteString
      getIdBytes();

  /**
   * <code>string firstName = 2;</code>
   * @return The firstName.
   */
  java.lang.String getFirstName();
  /**
   * <code>string firstName = 2;</code>
   * @return The bytes for firstName.
   */
  com.google.protobuf.ByteString
      getFirstNameBytes();

  /**
   * <code>string lastName = 3;</code>
   * @return The lastName.
   */
  java.lang.String getLastName();
  /**
   * <code>string lastName = 3;</code>
   * @return The bytes for lastName.
   */
  com.google.protobuf.ByteString
      getLastNameBytes();

  /**
   * <code>string phoneNum = 4;</code>
   * @return The phoneNum.
   */
  java.lang.String getPhoneNum();
  /**
   * <code>string phoneNum = 4;</code>
   * @return The bytes for phoneNum.
   */
  com.google.protobuf.ByteString
      getPhoneNumBytes();

  /**
   * <code>string srcCity = 5;</code>
   * @return The srcCity.
   */
  java.lang.String getSrcCity();
  /**
   * <code>string srcCity = 5;</code>
   * @return The bytes for srcCity.
   */
  com.google.protobuf.ByteString
      getSrcCityBytes();

  /**
   * <code>string dstCity = 6;</code>
   * @return The dstCity.
   */
  java.lang.String getDstCity();
  /**
   * <code>string dstCity = 6;</code>
   * @return The bytes for dstCity.
   */
  com.google.protobuf.ByteString
      getDstCityBytes();

  /**
   * <code>string date = 7;</code>
   * @return The date.
   */
  java.lang.String getDate();
  /**
   * <code>string date = 7;</code>
   * @return The bytes for date.
   */
  com.google.protobuf.ByteString
      getDateBytes();

  /**
   * <code>int32 offeredPlaces = 8;</code>
   * @return The offeredPlaces.
   */
  int getOfferedPlaces();

  /**
   * <code>int32 takenPlaces = 9;</code>
   * @return The takenPlaces.
   */
  int getTakenPlaces();

  /**
   * <code>int32 pd = 10;</code>
   * @return The pd.
   */
  int getPd();

  /**
   * <code>repeated string customers = 11;</code>
   * @return A list containing the customers.
   */
  java.util.List<java.lang.String>
      getCustomersList();
  /**
   * <code>repeated string customers = 11;</code>
   * @return The count of customers.
   */
  int getCustomersCount();
  /**
   * <code>repeated string customers = 11;</code>
   * @param index The index of the element to return.
   * @return The customers at the given index.
   */
  java.lang.String getCustomers(int index);
  /**
   * <code>repeated string customers = 11;</code>
   * @param index The index of the value to return.
   * @return The bytes of the customers at the given index.
   */
  com.google.protobuf.ByteString
      getCustomersBytes(int index);

  /**
   * <pre>
   *default = false
   * </pre>
   *
   * <code>bool sentByLeader = 12;</code>
   * @return The sentByLeader.
   */
  boolean getSentByLeader();
}
