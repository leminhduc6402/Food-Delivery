import React, { useContext, useRef, useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import { MyUserContext } from "../App";
import Apis, { endpoints } from "../configs/Apis";
import { toast } from "react-toastify";
import { useNavigate, useParams } from "react-router-dom";

const AddNewFood = () => {
  const { id } = useParams();
  const [user, dispatch] = useContext(MyUserContext);
  const nav = useNavigate();
  const [error, setError] = useState({});
  const [previewImage, setPreviewImage] = useState(null);
  const [food, setFood] = useState({
    name: "",
    price: "",
    foodType: "",
    available: "",
  });
  const image = useRef(null);
  const defaultAvatarURL =
    "https://res.cloudinary.com/dvyk1ajlj/image/upload/v1690620249/xofbyivlqlztrhwlz08u.png";

  const handleAddFood = async (e) => {
    e.preventDefault();

    const processFood = async () => {
      let form = new FormData();
      for (let field in food) {
        if (field !== "image") {
          form.append(field, food[field]);
        }
      }
      form.append("restaurantId", id);
      form.append("image", image.current.files[0] || defaultAvatarURL);
      let res = await Apis.post(endpoints.foods, form);
      if (res.status === 201) {
        toast.success("Thêm món ăn thành công");
        nav(-1);
      } else {
        toast.error("Thêm món ăn thất bại");
      }
    };
    const messageError = {};
    if (!food.name.trim()) {
      messageError.name = "Tên món ăn không được để trống";
    } else if (food.name.length < 3 || food.name.length > 45) {
      messageError.name = "Tên món ăn không hợp lệ (3 - 45 ký tự)";
    }

    if (!food.price.toString().trim()) {
      messageError.price = "Giá món ăn không được để trống";
    }

    if (!food.foodType.trim()) {
      messageError.foodType = "Loại món ăn không được để trống";
    } else if (food.foodType.length < 3 || food.foodType.length > 45) {
      messageError.foodType = "Loại món không hợp lệ (3 - 45 ký tự)";
      console.log(food.foodType.length);
    }

    if (!food.available.trim()) {
      messageError.available = "Vui lòng chọn một giá trị"
    }

    setError(messageError);
    if (Object.keys(messageError).length === 0) {
      processFood();
    }
  };

  const change = (evt, field) => {
    setFood((current) => {
      return { ...current, [field]: evt.target.value };
    });
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (e) => {
        // Đọc dữ liệu của tệp ảnh và gán nó vào state để hiển thị
        setPreviewImage(e.target.result);
      };

      reader.readAsDataURL(file);
    }
  };
  return (
    <>
      <Container className="mt-3">
        <h1 className="text-center">THÊM MÓN ĂN</h1>
        <Form onSubmit={handleAddFood}>
          <Form.Group className="mb-3">
            <Form.Label>Tên món ăn:</Form.Label>
            <Form.Control
              onChange={(e) => change(e, "name")}
              placeholder="Nhập tên món ăn"
            />
            {error.name && <span className="text-danger">{error.name}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Giá món ăn:</Form.Label>
            <Form.Control
              type="number"
              onChange={(e) => change(e, "price")}
              placeholder="Nhập giá món ăn"
            />
            {error.price && <span className="text-danger">{error.price}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Loại món ăn</Form.Label>
            <Form.Control
              placeholder="Nhập loại món ăn"
              onChange={(e) => change(e, "foodType")}
            />
            {error.foodType && (
              <span className="text-danger">{error.foodType}</span>
            )}
          </Form.Group>

          <Form.Group>
            <Form.Label>Tình trạng món ăn</Form.Label>
            <Form.Select
              onChange={(e) => change(e, "available")}
            >
              <option value="-1">-- Chọn tình trạng ---</option>
              <option value="0">Hết hàng</option>
              <option value="1">Còn hàng</option>
            </Form.Select>
            {error.available && <span className="text-danger">{error.available}</span>}
          </Form.Group>

          <Form.Group className="mt-3">
            <Form.Label>Ảnh đại diện</Form.Label>
            <Form.Control
              type="file"
              onChange={handleImageChange}
              ref={image}
              accept=".jpg,.jpeg,.png"
            />
          </Form.Group>
          {previewImage && (
            <div className="mt-2">
              <img
                src={previewImage}
                style={{ width: 150 }}
                alt="Ảnh tạm thời"
              />
            </div>
          )}

          <Form.Group className="mt-3">
            <Button type="submit">Thêm món ăn</Button>
          </Form.Group>
        </Form>
      </Container>
    </>
  );
};

export default AddNewFood;
