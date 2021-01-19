# import requests as req
#
# print("Printing start mode for customer requests:")
# r =req.get('http://localhost:8080/customerRequests')
# print(r.text)
#
# print("Posting a customer requests")
# print()
# headers = { 'Content-Type': 'application/json' }
# requestData = "{ \"firstName\": \"shai\" , \"lastName\": \"porath\", \"path\": [\"A\", \"B\"], \"departureDate\": \"1.1.11\", \"passengers\": 1}"
# r = req.post('http://localhost:8080/customerRequests', headers=headers, data = requestData)
# print(r.json())
#
# vote_str = "{\r\n        \"national_security_number\": " + str(nsn) + ",\r\n        \"candidate\": " + str(candidate) + ",\r\n        \"state\": " + "\"" + state + "\"" + "\r\n    }"

import requests
import numpy

# make sure its the same as the cities we have in the system (its currently not)
list_of_cities = ["a1", "a2", "a3", "b1", "b2", "c1", "c2"]


for i in range(20):
  srcCity = numpy.random.choice(list_of_cities)
  dstCity = numpy.random.choice(list_of_candidates)

  while srcCity == dstCity:
    dstCity = numpy.random.choice(list_of_candidates)

  # vote_str = "{\r\n        \"national_security_number\": " + str(nsn) + ",\r\n        \"candidate\": " + str(
  #   candidate) + ",\r\n        \"state\": " + "\"" + state + "\"" + "\r\n    }"

  ride = "{ \"firstName\": \"shai\" , \"lastName\": \"porath\", \"path\": [\"A\", \"B\"], \"departureDate\": \"1.1.11\", \"passengers\": 1}"
  customerRequest = "# fill both of them correctly"

  url = "http://localhost:9090/votes/"
  headers = {'Content-Type': 'application/json'}
  r1 = ride.post('http://localhost:8080/rides', headers=headers, data=requestData)
  r2 = customerRequest.post('http://localhost:8080/customerRequests', headers=headers, data=requestData)
  print(r1.json())
  print(r2.json())


# do a get request after the for
