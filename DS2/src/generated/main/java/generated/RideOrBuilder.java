// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: scheme.proto

package generated;

public interface RideOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Ride)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 id = 1;</code>
   * @return The id.
   */
  int getId();

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
   * <code>int32 phoneNum = 4;</code>
   * @return The phoneNum.
   */
  int getPhoneNum();

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
   * <code>int32 vacancies = 8;</code>
   * @return The vacancies.
   */
  int getVacancies();

  /**
   * <code>int32 pd = 9;</code>
   * @return The pd.
   */
  int getPd();
}
