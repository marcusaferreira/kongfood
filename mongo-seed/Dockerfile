FROM mongo

COPY product.json /product.json

CMD mongoimport --uri "mongodb://mongo_db:27017/kongfood" --collection product --type json --file /product.json --jsonArray