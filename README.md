# BonialCodeChallenge
Display list of brochures using Compose. In each cell display brochure image + retailer name.

**Architecture (MVVM with Clean Layers)**
├── data/
│   ├── remote/           # ApiService, DTOs
│   └── repository/       # ShelfRepositoryImpl
├── domain/               # Models, UseCases
└── presentation/
├── viewmodel/        # ShelfViewModel
└── ui/               # Composable Screens


Key Comparison
Feature	Moshi (@JsonClass)	kotlinx (@Serializable)
Speed	Faster (~15-20%)	Slightly slower
Build Times	Slower (kapt/ksp)	Faster (no annotation proc)
Multiplatform	Limited	Excellent
Java Interop	Better	Poor
Formats	JSON only	JSON, Protobuf, CBOR, etc.
Retrofit Support	First-class	Requires extra converter