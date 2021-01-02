/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utils;

/**
 * Common utilities for the Uber service.
 */
public class UberUtil {
    private static final double COORD_FACTOR = 1e7;
    /**
     * Parses the JSON input file containing the list of rides.
     */
//    public static List<Ride> parseRides() {
//        return new ArrayList<Ride>() {
//            {
//                add(
//                        Ride.newBuilder()
//                                .setName("f1")
//                                .setLocation(Point.newBuilder().setLatitude(10).setLongitude(10).build())
//                                .build()
//                );
//                add(
//                        Ride.newBuilder()
//                                .setName("f2")
//                                .setLocation(Point.newBuilder().setLatitude(55).setLongitude(10).build())
//                                .build()
//                );
//                add(
//                        Ride.newBuilder()
//                                .setName("f3")
//                                .setLocation(Point.newBuilder().setLatitude(12).setLongitude(14).build())
//                                .build()
//                );
//            }
//        };
//    }
//
//    /**
//     * Indicates whether the given feature exists (i.e. has a valid name).
//     */
//    public static boolean exists(Feature feature) {
//        return feature != null && !feature.getName().isEmpty();
//    }
}