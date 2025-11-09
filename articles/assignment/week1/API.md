## 소비자
### 재고 검색
GET /products?name=string

INPUT
```json
{
    "name": "apple"
}
```

OUTPUT 
```JSON
{
    "id": 1,
    "name": "apple",
    "price": 1000,
    "stock": 100 
}
```

### 재고 구매 : 주문 생성
POST /orders

INPUT
```JSON
[
  {
    "name": "apple",
    "quantity": 5
  },
  {
    "name": "banana",
    "quantity": 2
  }
]
```

OUTPUT
```JSON
{
    "totalPrice": 7000,
    "productInfo" : [
    {
        "name": "apple",
        "quantity": 5,
        "subPrice": 4000
    },
    {
        "name": "banana",
        "quantity": 2,
        "subPrice": 30000
    }
    ]
}
```

## 관리자
### 재고 등록
POST /admin/products

INPUT
```JSON
{
    "name": "apple",
    "price": 3000,
    "stock": 300
}
```

ERROR OUTPUT
```JSON
{
    "status": 409,
    "message": "상품명 'apple'은 이미 등록되어 있습니다."
}
```

### 재고 추가
PATCH /admin/products/{name}/stock
//이게 맞나

INPUT
```JSON
{
    "name" : "apple",
    "quantity" : 300
}
```

OUTPUT
```JSON
{
    "name" : "apple",
    "price": 3000,
    "stock" : 300
}
```

### 물품 삭제
DELETE /admin/products?name=string[]

INPUT
```JSON
{
   "name" : "apple"
}
```

    OUTPUT
```JSON
[
    {
        "name": "orange",
        "price": 2000,
        "stock": 50
    },
    {
      "name": "grape",
      "price": 1500,
      "stock": 20
    }
]
```