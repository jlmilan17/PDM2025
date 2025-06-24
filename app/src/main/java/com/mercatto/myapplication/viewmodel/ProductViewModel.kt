package com.mercatto.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercatto.myapplication.data.model.Product
import com.mercatto.myapplication.data.repository.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repo: ProductRepository = ProductRepository()
) : ViewModel() {

    //1. Productos traidos de la API
    private val _allProducts   = MutableStateFlow<List<Product>>(emptyList())
    private val _categories    = MutableStateFlow<List<String>>(emptyList())
    private val _selectedCat   = MutableStateFlow<String?>(null)
    private val _searchQuery   = MutableStateFlow("")
    private val _publishedList = MutableStateFlow<List<Product>>(emptyList())

    // 2) IDs de los productos marcados como favoritos
    // Empezamos con un Set vacío para evitar duplicados
    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    // 3) Lista de productos favoritos, combinando todos los productos con los IDs favoritos
    val favoriteProducts: StateFlow<List<Product>> = combine(
        _allProducts,
        _favoriteIds
    ) { products, favIds ->
        products.filter { it.id in favIds }
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val categories: StateFlow<List<String>> = _categories
    val selectedCategory: StateFlow<String?> = _selectedCat
    val searchQuery: StateFlow<String> = _searchQuery
    val publishedProducts: StateFlow<List<Product>> = _publishedList

    val filteredProducts: StateFlow<List<Product>> = combine(
        _allProducts,
        _selectedCat,
        _searchQuery
    ) { products, cat, query ->
        products
            .filter { cat == null || it.category.equals(cat, ignoreCase = true) }
            .filter { it.title.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            _categories.value  = repo.fetchCategories()
            _allProducts.value = repo.fetchProducts()
        }
    }

    // 5) Función para alternar favorito
    fun toggleFavorite(productId: Int) {
        val current = _favoriteIds.value.toMutableSet()
        if (productId in current) current -= productId
        else current += productId
        _favoriteIds.value = current
    }

    // 6) Helper para la UI
    fun isFavorite(productId: Int): Boolean =
        productId in _favoriteIds.value

    fun selectCategory(cat: String?) {
        _selectedCat.value = cat
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = repo.fetchCategories()
        }
    }

    fun publishProduct(product: Product) {
        viewModelScope.launch {
            repo.addProductToFirestore(product)
            val currentList = _publishedList.value.toMutableList()
            currentList.add(product)
            _publishedList.value = currentList
        }
    }


}