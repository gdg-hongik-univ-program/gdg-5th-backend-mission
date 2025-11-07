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
POST /order

```json
//Input
{
    "cart" : [ 
    {
        "name" : "apple", 
        "cnt" : 20 //물건 구매 갯수
    },
    {
        "name" : "water",
        "cnt" : 20
    }
    ]

}
```

```Json
// OUTPUT
{
    "totalPrice": 40000 ,
    "cart" : [
        {
            "name" : "apple",
            "cnt" : 20,
            "price" : 1000
        },
        {
            "name" : "water",
            "cnt" : 20,
            "price" : 1000

        }]
}
    
```

## 관리자

### 재고등록

POST /products
```Json
//Input 
{
    "name" : "apple",
    "stock" : 100,
    "price" : 1000
}
```

```Json
//Error
{
    "message" : "이미 존재하는 물품입니다"

}
```

### 재고추가
PATCH /products/1
```Json
//Input
{  
    "name" : "apple",
    "cnt" : 10
}
```

```Json
//Output
{
    "name" : "apple",
    "stock" : 110
}

```

### 물품삭제
DELETE /products
```Json
//Input
{
    "items" : [
    {"name" : "apple"},
    {"name" : "water"}
    ]
}
```

```Json
//Out
{
    "rest" : [{
        "name": "milk",
        "stock" : 20
    },
    {
        "name" : "orange",
        "stock" : 20
    }]
}
```