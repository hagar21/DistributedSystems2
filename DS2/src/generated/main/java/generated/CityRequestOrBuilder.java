// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: scheme.proto

package generated;

public interface CityRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:CityRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string destCityName = 1;</code>
   * @return The destCityName.
   */
  java.lang.String getDestCityName();
  /**
   * <code>string destCityName = 1;</code>
   * @return The bytes for destCityName.
   */
  com.google.protobuf.ByteString
      getDestCityNameBytes();

  /**
   * <code>.Rout rout = 2;</code>
   * @return Whether the rout field is set.
   */
  boolean hasRout();
  /**
   * <code>.Rout rout = 2;</code>
   * @return The rout.
   */
  generated.Rout getRout();
  /**
   * <code>.Rout rout = 2;</code>
   */
  generated.RoutOrBuilder getRoutOrBuilder();
}