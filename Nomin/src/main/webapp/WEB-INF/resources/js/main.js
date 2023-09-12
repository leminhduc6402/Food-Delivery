
document.addEventListener("DOMContentLoaded", function () {
    
    //update User
    const btnUpdateUser = document.getElementById("btnUpdateUser");
    if (btnUpdateUser !== null) {
        btnUpdateUser.addEventListener("click", function (event) {
            event.preventDefault();
            if (confirm("Bạn có chắc muốn cập nhật thông tin cá nhân không?")) {
                // Submit the form if confirmed
                const updateUserForm = document.getElementById("updateUserForm");
                updateUserForm.submit();
            }
        });      
    }
    const btnUpdateRes = document.getElementById("btnUpdateRes"); 
    if (btnUpdateRes !== null) {      
        btnUpdateRes. addEventListener("click", function (event){
            event.preventDefault();
            console.log("test");
            if (confirm("Bạn có chắc muốn cập nhật thông tin cửa hàng không?")){
                const updateResForm = document.getElementById("updateResForm");
                updateResForm.submit();
            }
        });
    }
    // Set ảnh đại diện tạm thời
    let imagePreview = document.getElementById("avatar-preview");
    let avatarUploadFile = document.getElementById("avatar");
    avatarUploadFile.onchange = (ev) => {
        if (ev.target.files && ev.target.files[0]) {
            imagePreview.src = URL.createObjectURL(ev.target.files[0]);
        }
    };
       
});

