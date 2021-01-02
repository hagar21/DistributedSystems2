import requests as req

print("Printing start mode for customer requests:")
r =req.get('http://localhost:8080/customerRequests')
print(r.text)

print("Posting a customer requests")
print()
headers = { 'Content-Type': 'application/json' }
requestData = "{ \"firstName\": \"shai\" , \"lastName\": \"porath\", \"path\": [\"A\", \"B\"], \"departureDate\": \"1.1.11\", \"passengers\": 1}"
r = req.post('http://localhost:8080/customerRequests', headers=headers, data = requestData)
print(r.json())