# 🍽️ MealApp — Recetas de Gastronomía Mundial

> Aplicación Android desarrollada en Java que consume la API pública [TheMealDB](https://www.themealdb.com/) para explorar categorías de comida, platos y recetas detalladas, como parte de un laboratorio académico.

## 📋 Tabla de Contenidos

- [Descripción](#-descripción-del-proyecto)
- [Tecnologías](#-tecnologías-usadas)
- [Estructura](#-estructura-principal)
- [Flujo Funcional](#-flujo-funcional-implementado)
- [APIs Consumidas](#-apis-consumidas-themealdb)
- [Estado](#-estado-frente-a-la-consigna)
- [Ejecución](#-cómo-ejecutar)

---

## 📝 Descripción del Proyecto

Una aplicación Android que permite al usuario:

✅ Ingresar a la app validando la conexión a Internet (diálogo con acceso a Ajustes si no hay)  
✅ Explorar las categorías de comida disponibles en TheMealDB  
✅ Listar los platos de una categoría o buscarlos por ingrediente principal  
✅ Visualizar la receta completa de un plato (instrucciones, origen e ingredientes)  
✅ Obtener un plato sorpresa aleatorio agitando el dispositivo (acelerómetro)

## 💻 Tecnologías Usadas

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| Java | 17 | Lenguaje base (sin Kotlin) |
| Android SDK | API 34 (min) – API 37 (target) | Plataforma |
| Navigation Component | 2.9.3 | Navegación entre fragmentos |
| Retrofit + Gson | 2.9.0 | Consumo de API REST |
| Glide | 4.16.0 | Carga de imágenes remotas |
| ViewBinding | — | Enlace de vistas |
| SensorManager | — | Acelerómetro (agitación) |
| TheMealDB API | v1/1 (key pública 1) | Fuente de datos |

## 📂 Estructura Principal

```
MealApp/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/mealapp/
│       │   ├── MainActivity.java
│       │   ├── AppActivity.java
│       │   ├── fragments/
│       │   │   ├── CategoriesFragment.java
│       │   │   ├── MealsFragment.java
│       │   │   └── RecipeFragment.java
│       │   ├── adapter/
│       │   │   ├── CategoryAdapter.java
│       │   │   └── MealAdapter.java
│       │   ├── dto/
│       │   │   ├── CategoriesResponse.java
│       │   │   ├── MealsResponse.java
│       │   │   └── MealDetailResponse.java
│       │   └── network/
│       │       ├── RetrofitClient.java
│       │       └── TheMealApi.java
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml
│           │   ├── activity_app.xml
│           │   ├── fragment_categories.xml
│           │   ├── fragment_meals.xml
│           │   ├── fragment_recipe.xml
│           │   ├── item_category.xml
│           │   └── item_meal.xml
│           ├── menu/bottom_nav_menu.xml
│           ├── navigation/nav_graph.xml
│           └── values/themes.xml
├── build.gradle
├── settings.gradle
└── README.md
```

## 🔄 Flujo Funcional Implementado

```
┌─────────────────────────────────────────────────────────────┐
│                    FLUJO DE LA APLICACIÓN                   │
├─────────────────────────────────────────────────────────────┤
│  1. MainActivity                                            │
│     ↓ Nombre app + imágenes + botón "Ingresar"              │
│     ↓ Valida conexión a Internet                            │
│     ↓ Sin conexión → Dialog "Configuración" → Ajustes       │
│                                                             │
│  2. AppActivity (NavHost + BottomNavigationView)            │
│     ↓ [Categorías] GET 1 → RecyclerView de categorías       │
│     ↓ click en categoría → action → Meals                   │
│                                                             │
│  3. MealsFragment                                           │
│     ↓ Con categoría → GET 2A (filter.php?c=)                │
│     ↓ Con ingrediente + "Buscar" → GET 2B (filter.php?i=)   │
│     ↓ click en plato → action → Recipe                      │
│     ↓ Agitación > 4 m/s² → GET 4 (random.php) → Recipe      │
│                                                             │
│  4. RecipeFragment                                          │
│     ↓ Con ID (manual o recibido) → GET 3 (lookup.php?i=)    │
│     ↓ Muestra nombre, categoría, origen,                    │
│       instrucciones e ingredientes                          │
└─────────────────────────────────────────────────────────────┘
```

Navegación con **popUpTo inclusivo**: el back stack nunca acumula fragmentos, por lo que el botón atrás regresa siempre al `MainActivity`.

## 🌐 APIs Consumidas (TheMealDB)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET 1 | `/categories.php` | Lista todas las categorías |
| GET 2A | `/filter.php?c={categoría}` | Platos por categoría |
| GET 2B | `/filter.php?i={ingrediente}` | Platos por ingrediente |
| GET 3 | `/lookup.php?i={idMeal}` | Detalle completo de un plato |
| GET 4 | `/random.php` | Plato aleatorio (agitación) |

**Envío de datos entre fragmentos:** `Bundle` con las claves `category` (Categories→Meals) y `mealId` (Meals→Recipe).

**Sensor:** acelerómetro registrado en `onResume` y desregistrado en `onPause` de `MealsFragment` (activo solo en ese fragmento). Umbral: `|√(x²+y²+z²) − 9.81| > 4 m/s²`, con cooldown de 3 s.

## ✅ Estado frente a la Consigna

### ✔️ Implementado

- [x] Pantalla principal con nombre de la app, imágenes gastronómicas y botón "Ingresar"
- [x] Validación de conexión a Internet antes de ingresar
- [x] Dialog "Configuración" que redirige a los Ajustes del dispositivo
- [x] `AppActivity` con menú de navegación inferior (3 pestañas)
- [x] Navigation Component con 3 fragmentos (Categories, Meals, Recipe)
- [x] Fragmentos fuera del back stack (back regresa al MainActivity)
- [x] RecyclerView de Categorías (GET 1): nombre + imagen
- [x] Click en categoría → Meals con GET 2A
- [x] Búsqueda por ingrediente con EditText + botón (GET 2B)
- [x] RecyclerView de Meals: nombre, ID e imagen
- [x] Click en plato → Recipe con carga automática del ID
- [x] Búsqueda de receta por ID manual (GET 3)
- [x] Detalle: nombre, categoría, área/origen, instrucciones e ingredientes (≥3)
- [x] Acelerómetro en el fragmento de búsqueda con umbral de 4 m/s²
- [x] Agitación → GET 4 (plato aleatorio) → redirección a Recipe

### 💡 Observación

⚠️ El acelerómetro usa `TYPE_ACCELEROMETER` con la magnitud menos la gravedad; en dispositivos sin este sensor la agitación no estará disponible (el resto de la app funciona igual).

## 🚀 Cómo Ejecutar

### Requisitos Previos

- Android Studio (con AGP 8.5+ y JDK 17)
- Emulador o dispositivo con Android 14 (API 34) o superior
- Conexión a Internet

### Ejecutar la Aplicación

1. Clonar o abrir el proyecto en Android Studio.
2. **File → Sync Project with Gradle Files**.
3. Seleccionar un emulador/dispositivo con API ≥ 34.
4. Presionar **Run ▶**.

### Probar la Agitación (Emulador)

1. Entrar a la pestaña **Platos**.
2. Abrir **Extended Controls (⋮)** → **Virtual Sensors** → **Accelerometer**.
3. Ingresar `X = 14, Y = 0, Z = 0` y presionar **Set** → la app navega a Receta con un plato aleatorio.

---

## 📚 Recursos Adicionales

- [TheMealDB API Documentation](https://www.themealdb.com/api.php)
- [Android Navigation Component](https://developer.android.com/guide/navigation)
- [Retrofit](https://square.github.io/retrofit/)
- [Glide](https://bumptech.github.io/glide/)

---

## 📄 Licencia

Este proyecto es de uso académico y educativo como parte de un laboratorio de curso universitario.
