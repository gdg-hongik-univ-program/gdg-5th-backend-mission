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
POST /products

```json
//Input
{
    "cart" : [ 
    {
        "name" : "apple",
        "cnt" : 20
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
    "total": 20000 ,
    "cart" : 
        {
            "name" : "apple",
            "cnt" : 20,
            "price" : 10000
        },
        {
            "name" : "water",
            "cnt" : 20,
            "price" : 10000

        }
}
    
```

## 관리자

### 재고등록

POST /products/1
```Json
//Input 
{
    "name" : "apple",
    "stock" : 100,
    "price" : 1000

}
```

```Json
//Oupt
{


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
```Json
//Input
{
    "name" : ["apple","water"]
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