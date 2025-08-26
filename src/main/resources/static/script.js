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

    // Validation - məcburi sahələri yoxlamaq
    if (!formData.fullName || !formData.email || !formData.phoneNumber) {
        showAlert('Please fill in all required fields', 'error');
        return;
    }

    // Telefon nömrəsi validation - Azərbaycan nömrə formatı
    const phoneRegex = /^\+0(50|51|55|70|77|99|10)\d{7}$/;
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
        const response = await fetch('http://localhost:8080/api/demo-appeals', {
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
        // Demo məqsədli: Əgər backend əlaqəsi yoxdursa, success səhifəsini göstər
        showSuccessPage();
    }
});

// Alert göstərmək
function showAlert(message, type) {
    const alertBox = document.getElementById('alertBox');
    alertBox.textContent = message;
    alertBox.className = `alert alert-${type}`;
    alertBox.style.display = 'block';

    // 5 saniyədən sonra alerti gizlət
    setTimeout(() => {
        alertBox.style.display = 'none';
    }, 5000);
}

// Uğur səhifəsini göstərmək
function showSuccessPage() {
    document.getElementById('main-container').style.display = 'none';
    document.getElementById('success-container').style.display = 'flex';
}

// Geri dönmək
function goBack() {
    document.getElementById('success-container').style.display = 'none';
    document.getElementById('main-container').style.display = 'flex';
    document.getElementById('demoAppealForm').reset();
}

// Telefon nömrəsi formatına avtomatik çevirmək
document.getElementById('phone').addEventListener('input', function(e) {
    // Yalnız rəqəmlərə icazə ver
    this.value = this.value.replace(/\D/g, '');

    // Maksimum 9 rəqəm (994 + 9 rəqəm = 12 rəqəm)
    if (this.value.length > 9) {
        this.value = this.value.slice(0, 9);
    }
});