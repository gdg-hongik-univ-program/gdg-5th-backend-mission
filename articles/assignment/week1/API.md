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
### 재고구매

POST /purchase

```json
// INPUT -> 구매할 개수입력
{
		"items":[
				{ "name": "apple", "quantity": 3},
				{ "name": "banana", "quantity": 2}
		]
}
```

```json
// OUTPUT
{
		"totalAmount":7000, // 총구매액
		// 물건별 소비금액
		"items":[
			{
				"name": "apple", // 이름
				"quantity": 3, // 구매개수
				"cost": 3000 // 해당 물건의 총가격
			},
			{
				"name": "banana",
				"quantity": 2,
				"cost": 4000
			}
		] 
}
```

### 재고등록

POST /products

```json
// INPUT
{
	"name": "strawberry",
	"price": 3000,
	"stock": 50
}
```

```json
// OUTPUT
{
	items":{
		"id": 10,
		"name": "strawberry",
		"price": 3000,
		"stock": 50
	}
}
```

### 재고추가

PATCH /products/{id}

```json
// INPUT -> 추가할 재고수 입력
{
	"addStock": 20 
}
```

```json
// OUTPUT 
{
	"item":{
		"id":10,
		"name": "strawberry",
		"price": 3000,
		"stock": 70 // 추가 후 재고 수 반환
	}
}
```

### 물품 삭제

DELETE /products

```json
// INPUT
{
	"names": ["apple", "banana"] // 삭제할 물품들의 이름, 1개 이상일 수 있음
}
```

```json
// OUTPUT
{
	remainItems:[
		{"id": 10, "name": "strawberry", "stock":70}
	]
}
```

