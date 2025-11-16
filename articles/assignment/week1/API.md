## 소비자

### 재고 검색

```json
GET /products?name=string // ? 뒤는 검색용 옵션 쿼리 파라미터.

// INPUT
{
	"name" : "apple" // 검색할 물건에 대한 이름
}

// OUTPUT
{
	"id": 1, // 물건별 고유 ID
	"name": "apple",
	"price": 1000,
	"stock": 100 // 재고 수
}
```

### 재고 구매

```json
POST /products/purchase

//INPUT
{
	"items" = [ // 물건을 구매하기 위해서는 상품, 수량 등 여러 항목이 필요하다. [] 안에 {}로 나눈다.
	// 또한, id 혹은 이름과 수량만으로 API를 구현할 수도 있지만 우선 모든 정보를 포함시킴.
		{
			"id": 1,
			"name": "apple",
			"price": 1000,
			"quantity": 2 // 구매 수량
		},
		{
			"id": 2,
			"name": "banana",
			"price": 2000,
			"quantity": 3
		}
	]
}

//OUTPUT
{
	"totalPrice": 8000,
	"items": [
		{
			"name": "apple",
			"quantity": 2,
			"spend" : 2000
		},
		{
			"name": "banana",
			"quantity": 3,
			"spend": 6000
		}
	]
}
```

## 관리자

### 재고 등록

```json
POST /admin/products

//INPUT
{
	"name": "apple",
	"price" : 1000,
	"stock" : 100
}

//OUTPUT
{
	"id": 1,
	"name": "apple",
	"price": 1000,
	"stock": 100
}
```

### 재고 추가

```json
PATCH /admin/products/stock

//INPUT
{
	"id": 1,
	"addStock": 20
}

//OUTPUT
{
	"id": 1,
	"name": "apple",
	"price": 1000,
	"totalStock": 120
}
```

### 재고 삭제

```json
DELETE /admin/products

//INPUT
{
	"ids" = [1, 2] // 각 항목이 단일 값 "id" 만 필요하므로 []면 충분.
}

//OUTPUT
{
	"ProductsList": [
		{
			"id" = 3,
			"name": "lemon",
			"price": 3000,
			"stock": 50
		},
		{
			"id" = 4,
			"name": "golden peach"
			"price": 999999999,
			"stock": 1
		}
	]
}
```