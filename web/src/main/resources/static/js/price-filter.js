document.addEventListener('DOMContentLoaded', function() {
    const minInput = document.getElementById('minPriceInput');
    const maxInput = document.getElementById('maxPriceInput');
    const applyBtn = document.getElementById('applyFilter');
    const sortOptions = document.querySelectorAll('.sort-option');
    const currentSortText = document.getElementById('currentSortText');

    const params = new URLSearchParams(window.location.search);

    // Update the sort dropdown text based on URL
    if (params.has('sort')) {
        const currentSort = params.get('sort');
        const activeOption = document.querySelector(`.sort-option[data-sort="${currentSort}"]`);
        if (activeOption && currentSortText) {
            currentSortText.innerText = 'Sort By: ' + activeOption.innerText;
        }
    }

    // Handle Sorting clicks
    sortOptions.forEach(option => {
        option.addEventListener('click', function(e) {
            e.preventDefault();
            const sortVal = this.getAttribute('data-sort');
            const currentUrl = new URL(window.location.href);
            
            currentUrl.searchParams.set('sort', sortVal);
            currentUrl.searchParams.set('page', '0'); // Reset to page 0 on new sort
            window.location.href = currentUrl.toString();
        });
    });

    if (minInput && maxInput && applyBtn) {
        
        // Basic validation for min/max inputs
        minInput.oninput = function() {
            if (parseInt(this.value) < 0) this.value = 0;
        }
        maxInput.oninput = function() {
            if (parseInt(this.value) < 0) this.value = 0;
        }

        // Apply filters
        applyBtn.onclick = function() {
            const min = minInput.value;
            const max = maxInput.value;
            
            const currentUrl = new URL(window.location.href);
            
            if (min) currentUrl.searchParams.set('minPrice', min);
            else currentUrl.searchParams.delete('minPrice');
            
            if (max) currentUrl.searchParams.set('maxPrice', max);
            else currentUrl.searchParams.delete('maxPrice');

            currentUrl.searchParams.set('page', '0');
            window.location.href = currentUrl.toString();
        }

        // Initialize values from URL
        if (params.has('minPrice')) minInput.value = params.get('minPrice');
        if (params.has('maxPrice')) maxInput.value = params.get('maxPrice');
    }
});
