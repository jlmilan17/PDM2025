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

    private val _allProducts   = MutableStateFlow<List<Product>>(emptyList())
    private val _categories    = MutableStateFlow<List<String>>(emptyList())
    private val _selectedCat   = MutableStateFlow<String?>(null)
    private val _searchQuery   = MutableStateFlow("")
    private val _publishedList = MutableStateFlow<List<Product>>(emptyList())

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
        val currentList = _publishedList.value.toMutableList()
        currentList.add(product)
        _publishedList.value = currentList
    }
}