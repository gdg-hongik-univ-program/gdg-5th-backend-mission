## 소비자
### 재고 검색
GET /products?name=string

```json
// INPUT
{
    "name": "apple"// 검색할 물건에 대한 이름
}
```

```JSON
// OUTPUT
{
    "id": 1, // 물건별 고유 ID
    "name": "apple",
    "price": 1000,
    "stock": 100 // 물건 재고 수   
}
```

### 재고 구매
Post /purchase

```json
//input
{
  "items": [
    { "name": "apple",  "quantity": 2 },  
    { "name": "banana", "quantity": 5 }
  ]
}
```
```JSON
// OUTPUT
{
  "totalAmount": 7000,            
  "items": [
    {
      "name": "apple",                   
      "quantity": 2,                 
      "amount": 2000                     
    },
    {
      "name": "banana",
      "quantity": 5,
      "amount": 5000
    }
  ]
}
```
## 관리자
### 재고 등록
POST /products

```json
// INPUT
{
    "name": "apple",
    "price": 1000,
    "stock": 100
}
```

```JSON
// OUTPUT
{
    "id": 1, 
    "name": "apple",
    "price": 1000,
    "stock": 100 
}
```

HTTP 409 Conflict
```JSON
{
//Error
"error": "PRODUCT_ALREADY_EXISTS",
"message": "이미 존재하는 상품 이름입니다."
}
```

### 재고 추가
PATCH /products/{productId}

```json
// INPUT
{
    "quantity": 50
}
```

```JSON
// OUTPUT
{
    "stock":100
}
```

### 물품 삭제
DELETE /products

```json
// INPUT
{
    "ids": [1,5,9]
}
```

```JSON
// OUTPUT
{
  "items": [
    {
      "id": 2,
      "name": "banana",
      "stock": 80 
    },
    {
      "id": 3,
      "name": "orange",
      "stock": 40
    }
  ],
  "totalProducts": 2  
}
```



