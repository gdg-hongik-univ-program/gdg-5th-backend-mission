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
POST /orders

```json
// INPUT
{
      "itemsToPurchase": [
        {
            "name": "apple", // 구매 할 물건의 이름
            "count": 3       // 구매 할 물건의 개수
        },
        {
            "name": "orange",
            "count": 5
        }
    ]
}
```

```JSON
// OUTPUT
{
    "totalPrice": 13000,    // 총 구매액
    "items": [ // 각 물건의 정보
        {
            "name": "apple" // 이름
            "count": 3      // 구매한 개수
            "price": 3000   // 해당 물건에서 소비한 금액
        },
        {
            "name": "orange"
            "count": 5
            "price": 10000
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
    "name": "apple"
    "count": 3
    "price": 3000
}
```

### 재고 추가 
PATCH /products/{productId}

```json
// INPUT
{
    "name": "apple" // 추가 할 상품의 이름
    "count": 10     // 추가 할 개수
}
```

```JSON
// OUTPUT
{
    "name": "apple"
    "stock": 110
}
```
## 물품 삭제
DELETE /products/{productId}

```json
// INPUT
{
    "name": "apple" // 삭제 할 상품의 이름
}
```

```JSON
// OUTPUT
{
    "name": "orange"
    "stock": 20
}
```
