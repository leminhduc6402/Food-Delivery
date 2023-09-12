import React, { useContext, useEffect, useRef, useState } from "react";
import { Button, Col, Container, Form, Row, Spinner } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import Apis, { endpoints } from "../configs/Apis";
import { MyUserContext } from "../App";

const DetailFood = () => {
  const nav = useNavigate();
  const [user, dispatch] = useContext(MyUserContext);
  useEffect(() => {
    if (user === null || user.role !== "ROLE_RESTAURANT") {
      return nav("/");
    }
  }, []);
  const { id } = useParams();
  const [evaluate, setEvaluate] = useState(0);
  const [error, setError] = useState({});
  const [previewImage, setPreviewImage] = useState(null);
  const image = useRef(null);

  const [food, setFood] = useState({
    id: "",
    name: "",
    price: "",
    foodType: "",
    restaurantId: "",
    available: "",
  });
  useEffect(() => {
    const fetchFoodDetail = async () => {
      let endpoint = endpoints.getFoodById;
      await Apis.get(`${endpoint}${id}`)
        .then((res) =>
          setFood((preValues) => ({
            ...preValues,
            id: res.data.id,
            name: res.data.name,
            price: res.data.price,
            foodType: res.data.foodType,
            restaurantId: res.data.restaurantId.id,
            available: res.data.available,
            image: res.data.image,
          }))
        )
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    fetchFoodDetail();
  }, [id]);

  if (food === null) {
    return (
      <div className="text-center my-5">
        <Spinner animation="border" />
      </div>
    );
  }

  const change = (evt, field) => {
    setFood((current) => {
      return { ...current, [field]: evt.target.value };
    });
  };

  const UpdateFood = async (e) => {
    e.preventDefault();

    const processFood = async () => {
      let formData = new FormData();
      for (let field in food) {
        if (field !== "image") {
          formData.append(field, food[field]);
        }
      }
      if (image.current.files[0]) {
        formData.append("image", image.current.files[0]);
      }
      let res = await Apis.post(endpoints.updateFood(id), formData);

      if (res.status === 200) {
        toast.success("Cập nhật thành công");
        nav(-1);
      } else {
        toast.error("Cập nhật thất bại");
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
    setError(messageError);
    if (Object.keys(messageError).length === 0) {
      processFood();
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (e) => {
        setPreviewImage(e.target.result);
      };

      reader.readAsDataURL(file);
    }
  };

  return (
    <>
      <Container className="mt-3">
        <h1 className="text-center">QUẢN LÝ MÓN ĂN</h1>

        <Form onSubmit={UpdateFood}>
          <Form.Group className="mb-3">
            <Form.Label>Tên món ăn:</Form.Label>
            <Form.Control
              value={food.name}
              onChange={(e) => change(e, "name")}
            />
            {error.name && <span className="text-danger">{error.name}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Giá món ăn:</Form.Label>
            <Form.Control
              type="number"
              value={food.price}
              onChange={(e) => change(e, "price")}
            />
            {error.price && <span className="text-danger">{error.price}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Tình trạng món ăn:</Form.Label>
            <Form.Select
              value={food.available === "1" ? "1" : "0"}
              onChange={(e) => {
                change(e, "available")
              }}
            >
              <option value="1">Còn hàng</option>
              <option value="0">Hết hàng</option>
            </Form.Select>
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Loại món ăn:</Form.Label>
            <Form.Control
              value={food.foodType}
              onChange={(e) => change(e, "foodType")}
            />
            {error.foodType && (
              <span className="text-danger">{error.foodType}</span>
            )}
          </Form.Group>

          <Form.Group>
            <Form.Label>Ảnh đại diện</Form.Label>
            <Form.Control
              type="file"
              onChange={handleImageChange}
              ref={image}
              accept=".jpg,.jpeg,.png"
            />
          </Form.Group>
          <div className="mt-2">
            <img
              src={previewImage || food.image}
              style={{ width: 150 }}
              alt="Ảnh tạm thời"
            />
          </div>
          <Form.Group className="mt-3">
            <Button type="submit">Cập nhật</Button>
          </Form.Group>
        </Form>
      </Container>
    </>
  );
};

export default DetailFood;
