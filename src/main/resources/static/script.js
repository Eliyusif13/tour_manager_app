// Form göndərildikdə
document.getElementById('demoAppealForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    // Form məlumatlarını toplamaq
    const formData = {
        fullName: document.getElementById('fullName').value,
        email: document.getElementById('email').value,
        phoneNumber: "0" + document.getElementById('phone').value,
        companyName: document.getElementById('companyName').value,
        message: document.getElementById('message').value
    };

    if (!formData.fullName || !formData.email || !formData.phoneNumber) {
        showAlert('Please fill in all required fields', 'error');
        return;
    }

    const phoneRegex = /^0(50|51|55|70|77|99|10)\d{7}$/;
    if (!phoneRegex.test(formData.phoneNumber)) {
        showAlert('Please enter a valid Azerbaijani phone number', 'error');
        return;
    }

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
        showAlert('Please enter a valid email address', 'error');
        return;
    }

    try {
        // API-ə POST sorğusu göndərmək
        const response = await fetch('http://localhost:8080/api/public/demo-appeals', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                fullName: formData.fullName,
                email: formData.email,
                companyName: formData.companyName,
                message: formData.message,
                phoneNumbers: [{ phoneNumber: formData.phoneNumber }],
                address: {
                    addressAz: "",
                    addressEn: "",
                    addressRu: ""
                }
            })
        });

        if (response.ok) {
            // Uğur səhifəsinə keçid
            showSuccessPage();
        } else {
            const errorData = await response.json();
            showAlert(`Error: ${errorData.error || 'Request failed'}`, 'error');
        }
    } catch (error) {
        showAlert('Error: ' + error.message, 'error');
        // Demo məqsədi: Əgər backend əlaqəsi yoxdursa, success səhifəsini göstər
        showSuccessPage();
    }
});

function showAlert(message, type) {
    const alertBox = document.getElementById('alertBox');
    alertBox.textContent = message;
    alertBox.className = `alert alert-${type}`;
    alertBox.style.display = 'block';

    setTimeout(() => {
        alertBox.style.display = 'none';
    }, 5000);
}

function showSuccessPage() {
    document.getElementById('main-container').style.display = 'none';
    document.getElementById('success-container').style.display = 'flex';
}

function goBack() {
    document.getElementById('success-container').style.display = 'none';
    document.getElementById('main-container').style.display = 'flex';
    document.getElementById('demoAppealForm').reset();
}

document.getElementById('phone').addEventListener('input', function(e) {
    // Yalnız rəqəmlərə icazə ver
    this.value = this.value.replace(/\D/g, '');

    // Maksimum 9 rəqəm (994 + 9 rəqəm = 12 rəqəm)
    if (this.value.length > 9) {
        this.value = this.value.slice(0, 9);
    }
});

// FAQ butonu
document.getElementById('faqButton').addEventListener('click', function() {
    showFAQPage();
    loadFAQs(); // Header ilə dil göndərilir
});

function showFAQPage() {
    document.getElementById('main-container').style.display = 'none';
    document.getElementById('success-container').style.display = 'none';
    document.getElementById('faq-container').style.display = 'flex';
}

// FAQ-ları yükləmək - İNDİ HEADER İLƏ DİL GÖNDƏRİLİR
async function loadFAQs() {
    const faqList = document.getElementById('faqList');
    faqList.innerHTML = '<div class="faq-loading">Sual və cavablar yüklənir...</div>';

    try {
        console.log('FAQ-lar database-dən yüklənir...');

        // DÜZƏLDİ: Header ilə dil göndərilir
        const response = await fetch('http://localhost:8080/api/public/faqs', {
            method: 'GET',
            headers: {
                'Accept-Language': 'az', // Azərbaycan dili
                'Content-Type': 'application/json'
            }
        });

        console.log('Response status:', response.status);

        if (response.ok) {
            const faqs = await response.json();
            console.log('Database-dən FAQ-lar alındı:', faqs);
            displayFAQs(faqs);
        } else {
            console.error('Xəta statusu:', response.status);
            const errorText = await response.text();
            console.error('Xəta mətni:', errorText);
            faqList.innerHTML = '<div class="faq-loading">Xəta baş verdi. Zəhmət olmasa sonra yenidən cəhd edin.</div>';
        }
    } catch (error) {
        console.error('FAQ yüklənərkən xəta:', error);
        // Xəta halında belə database-ə cəhd et
        try {
            await loadFAQsWithQueryParam(); // Query param ilə cəhd edək
        } catch (fallbackError) {
            console.error('Fallback də xəta:', fallbackError);
            faqList.innerHTML = `
                <div class="faq-loading">
                    <p>Xəta baş verdi: ${fallbackError.message}</p>
                    <p>Zəhmət olmasa backend serverin işlədiyindən əmin olun.</p>
                </div>
            `;
        }
    }
}

// Əlavə: Query param ilə cəhd etmək
async function loadFAQsWithQueryParam() {
    const faqList = document.getElementById('faqList');

    try {
        console.log('Query param ilə FAQ-lar yüklənir...');

        const response = await fetch('http://localhost:8080/api/public/faqs/az', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const faqs = await response.json();
            console.log('Query param ilə FAQ-lar alındı:', faqs);
            displayFAQs(faqs);
        } else {
            throw new Error(`HTTP xəta: ${response.status}`);
        }
    } catch (error) {
        throw error;
    }
}

// FAQ-ları ekranda göstərmək
function displayFAQs(faqs) {
    const faqList = document.getElementById('faqList');

    if (!faqs || faqs.length === 0) {
        faqList.innerHTML = '<div class="faq-loading">Hələlik heç bir sual-cavab yoxdur.</div>';
        return;
    }

    let html = '';
    faqs.forEach((faq, index) => {
        html += `
            <div class="faq-item">
                <div class="faq-question" onclick="toggleFAQ(this)">
                    <span style="font-weight: bold; margin-right: 8px;">${index + 1}.</span>
                    ${faq.question || 'Sual'}
                    <span class="faq-icon">▼</span>
                </div>
                <div class="faq-answer">
                    ${faq.answer || 'Cavab'}
                </div>
            </div>
        `;
    });

    faqList.innerHTML = html;
}

// FAQ açmaq/bağlamaq
function toggleFAQ(element) {
    const answer = element.nextElementSibling;
    const icon = element.querySelector('.faq-icon');

    if (answer.style.display === 'block') {
        answer.style.display = 'none';
        icon.classList.remove('rotated');
    } else {
        answer.style.display = 'block';
        icon.classList.add('rotated');
    }
}

// FAQ səhifəsindən geri qayıtmaq
function goBackFromFaq() {
    document.getElementById('faq-container').style.display = 'none';
    document.getElementById('main-container').style.display = 'flex';
}

// Səhifə yükləndikdə feature-ləri götür
document.addEventListener('DOMContentLoaded', function() {
    loadFeatures(); // Default olaraq Azərbaycan dili
});

// Feature-ləri yükləmək
async function loadFeatures() {
    const featuresGrid = document.getElementById('featuresGrid');
    featuresGrid.innerHTML = '<div class="feature-loading">Xüsusiyyətlər yüklənir...</div>';

    try {
        console.log('Feature-lər database-dən yüklənir...');

        const response = await fetch('http://localhost:8080/api/public/features', {
            method: 'GET',
            headers: {
                'Accept-Language': 'az', // Azərbaycan dili
                'Content-Type': 'application/json'
            }
        });

        console.log('Features response status:', response.status);

        if (response.ok) {
            const features = await response.json();
            console.log('Database-dən feature-lər alındı:', features);
            displayFeatures(features);
        } else {
            console.error('Features xəta statusu:', response.status);
            displaySampleFeatures();
        }
    } catch (error) {
        console.error('Feature-lər yüklənərkən xəta:', error);
        displaySampleFeatures();
    }
}

function displayFeatures(features) {
    const featuresGrid = document.getElementById('featuresGrid');

    if (!features || features.length === 0) {
        featuresGrid.innerHTML = '<div class="feature-loading">Hələlik heç bir xüsusiyyət yoxdur.</div>';
        return;
    }

    let html = '';
    features.forEach(feature => {
        html += `
            <div class="feature-card">
                ${feature.imagePath ? `
                    <img src="http://localhost:8080/${feature.imagePath}" 
                         alt="${feature.title}" 
                         class="feature-image"
                         style="width: 60px; height: 60px; border-radius: 50%; margin-bottom: 1rem;">
                ` : `
                    <div class="feature-icon">⭐</div>
                `}
                <h3 class="feature-title">${feature.title || 'Xüsusiyyət'}</h3>
                <p class="feature-text">${feature.text || 'Təsvir'}</p>
            </div>
        `;
    });

    featuresGrid.innerHTML = html;
}

function displaySampleFeatures() {
    const featuresGrid = document.getElementById('featuresGrid');
    const sampleFeatures = [
        {
            title: "Sürətli Rezervasiya",
            text: "24/7 onlayn rezervasiya xidməti"
        },
        {
            title: "7/24 Dəstək",
            text: "Hər zaman sizin xidmətinizdəyik"
        },
        {
            title: "Peşəkar Komanda",
            text: "Təcrübəli bələdçi və agentlər"
        }
    ];

    let html = '';
    sampleFeatures.forEach(feature => {
        html += `
            <div class="feature-card">
                <div class="feature-icon">⭐</div>
                <h3 class="feature-title">${feature.title}</h3>
                <p class="feature-text">${feature.text}</p>
            </div>
        `;
    });

    featuresGrid.innerHTML = html;
}