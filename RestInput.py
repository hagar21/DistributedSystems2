
import requests
import numpy

# make sure its the same as the cities we have in the system (its currently not)
list_of_cities = ["\"a1\"", "\"a2\"", "\"b1\"", "\"b2\""]

# ride = "{ \"firstName\": \"shai\", \"lastName\": \"porath\", \"phoneNumber\": \"0123\", \"startingPosition\": \"a1\", \"endingPosition\": \"a2\", \"departureDate\": \"1.19.21\", \"vacancies\": 4, \"pd\": 3}"
# url = "http://localhost:9090/votes/"
# headers = {'Content-Type': 'application/json'}
#
# print("Posting a post ride requests for ride:")
# print(ride)
# r1 = requests.post('http://localhost:8080/rides', headers=headers, data=ride)
#
# print("reply:")
#
# print(r1.text.encode('utf8'))
# exit()

for i in range(20):
  vac = numpy.random.choice(range(5))
  pd = numpy.random.choice(range(6))
  pathLen = numpy.random.choice(range(2, 4))
  srcCity = numpy.random.choice(list_of_cities)
  dstCity = numpy.random.choice(list_of_cities)

  while srcCity == dstCity:
    dstCity = numpy.random.choice(list_of_cities)

  path = ""

  for _ in range(pathLen):
    path = path + numpy.random.choice(list_of_cities) + ", "

  path = path + numpy.random.choice(list_of_cities)

  ride = "{ \"firstName\": \"shai" + str(i) + "\", \"lastName\": \"porath\", \"phoneNumber\": \"0123\", \"startingPosition\": " + srcCity + ", \"endingPosition\": " + dstCity + ", \"departureDate\": \"1.19.21\", \"vacancies\": " + str(vac) + ", \"pd\": " + str(pd) + "}"
  customerRequest = "{ \"name\": \"hagar" + str(i) + "\", \"path\": [" + path + "], \"departureDate\": \"1.19.21\"}"


  url = "http://localhost:9090/votes/"
  headers = {'Content-Type': 'application/json'}

  print("Posting a post ride requests for ride:")
  print(ride)
  r1 = requests.post('http://localhost:8080/rides', headers=headers, data=ride)

  print("reply:")
  print(r1.text.encode())
  print("------------------------------")

  print("Posting a customer requests")
  print(customerRequest)
  r2 = requests.post('http://localhost:8080/customerRequests', headers=headers, data=customerRequest)
  print("reply:")
  print(r2.text.encode())
  print("------------------------------")


print("snapshot:")
r = requests.get('http://localhost:8080/rides')
print(r.text.encode('utf8'))
r = requests.get('http://localhost:8080/customerRequests')
print(r.text.encode('utf8'))
