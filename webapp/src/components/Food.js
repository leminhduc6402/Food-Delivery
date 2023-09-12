import React, { useContext, useEffect, useRef, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Apis, { authApi, endpoints } from "../configs/Apis";
import { Button, Col, Row, Spinner } from "react-bootstrap";
import { toast } from "react-toastify";
import { MyCartContext, MyUserContext } from "../App";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import StarRating from "./StarRating";

const Food = () => {
  const { id } = useParams();
  const nav = useNavigate();
  const [user, dispatch] = useContext(MyUserContext);
  const [cartCounter, cartDispatch] = useContext(MyCartContext);
  const [food, setFood] = useState(null);
  const [evaluate, setEvaluate] = useState(0);
  const [quantity, setQuantity] = useState(1);
  const quantityInputRef = useRef(null);
  const stars = Array(5).fill(0);
  const [currentValue, setCurrentValue] = useState(0);
  const [hover, setHover] = useState(undefined);
  const [comments, setComments] = useState([]);
  const [content, setContent] = useState();
  const [isCmt, setIsCmt] = useState(true);

  useEffect(() => {
    const fetchComments = async () => {
      let endpoint = endpoints.foods;
      await Apis.get(`${endpoint}${id}/comments`).then((res) =>
        setComments(res.data)
      );
    };

    fetchComments();
  }, [id, isCmt]);

  useEffect(() => {
    const fetchFoodDetail = async () => {
      let endpoint = endpoints.getFoodById;
      await Apis.get(`${endpoint}${id}`)
        .then((res) => setFood(res.data))
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    fetchFoodDetail();
  }, [isCmt]);

  useEffect(() => {
    const fetchEvaluate = async () => {
      let endpoint = endpoints.getFoodById;
      await Apis.get(`${endpoint}${id}/getAverageRateForFood`)
        .then((response) => setEvaluate(response.data))
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    fetchEvaluate();
  }, [id]);

  if (!food) {
    return <Spinner animation="border" />;
  }
  const handleQuantity = (event) => {
    const newQuantity = parseInt(event.target.value, 10);
    if (newQuantity < 1 || isNaN(newQuantity)) {
      setQuantity(1);
    } else {
      setQuantity(newQuantity);
    }
  };
  const order = (food) => {
    const quantity = parseInt(quantityInputRef.current.value, 10); // Lấy giá trị của input

    if (quantity > 0) {
      cartDispatch({
        type: "inc",
        payload: quantity,
      });
      let cart = JSON.parse(localStorage.getItem("cart") || null);
      if (cart == null) {
        cart = {};
      }
      if (food.id in cart) {
        cart[food.id]["quantity"] += quantity;
      } else {
        cart[food.id] = {
          id: food.id,
          name: food.name,
          quantity: quantity,
          price: food.price,
          restaurantId: food.restaurantId,
        };
      }
      localStorage.setItem("cart", JSON.stringify(cart));
      toast.success(`Thêm thành công ${quantity} sản phẩm vào giỏ hàng`);
    } else {
      toast.error("Số lượng không hợp lệ");
    }
  };

  const handleClick = (value) => {
    setCurrentValue(value);
  };
  const handleMouseOver = (value) => {
    setHover(value);
  };
  const handleMouseLeave = () => {
    setHover(undefined);
  };

  const handleSubmitComment = () => {
    const process = async () => {
      let res = await authApi().post(endpoints.comments, {
        content: content,
        rate: currentValue,
        usersId: user,
        foodsId: food,
      });

      if (res.status === 201) {
        toast.success("Bình luận thành công");
        setContent("");
        setCurrentValue(0);
        setIsCmt(false);
      }
    };
    process();
  };

  if (user === null) {
    return nav("/login");
  }
  return (
    <>
      <div className="container">
        <div>
          <Row className="my-3 p-3">
            <Col sm={4} className="text-center">
              <img
                src={food.image}
                alt="Ảnh món ăn"
                style={{ width: 400, height: 300, objectFit: "cover" }}
              />
            </Col>
            <Col sm={8}>
              <div className="d-flex flex-column">
                <h3>{food.name}</h3>
                <div>
                  <h5 style={{ color: "#0288d1" }}>
                    {food.price}
                    <span
                      style={{
                        position: "relative",
                        top: "-9px",
                        fontSize: "10px",
                        right: 0,
                        fontWeight: 400,
                      }}
                    >
                      đ
                    </span>
                  </h5>
                </div>
                <div className="mb-2">{food.foodType}</div>
                <div className="d-flex mb-3">
                  {" "}
                  Đánh giá:
                  <StarRating averageRating={evaluate} />
                </div>
                <div className="mb-2">
                  <input
                    type="number"
                    ref={quantityInputRef}
                    defaultValue={1}
                    min={1}
                    value={quantity}
                    onBlur={handleQuantity}
                    onChange={(event) => setQuantity(event.target.value)}
                  />
                </div>
                <div>
                  <Button variant="outline-success" onClick={() => order(food)}>
                    Thêm vào giỏ hàng
                  </Button>
                </div>
              </div>
            </Col>
          </Row>
        </div>
        <div className="shadow-lg p-2 mb-3 bg-body rounded gap-3">
          <h3 className="bg-primary bg-gradient text-white p-3">
            Bình luận và đánh giá
          </h3>
          <div className="p-3">
            <div className="border-bottom p-2">
              <Row>
                <Col sm={1} className="text-center">
                  <img
                    src={user.avatar}
                    width={60}
                    height={60}
                    className="rounded-circle"
                  />
                </Col>
                <Col sm={11} className="gap-2 d-flex flex-column">
                  <div>
                    {stars.map((_, index) => {
                      return (
                        <FontAwesomeIcon
                          icon={faStar}
                          key={index}
                          size="1x"
                          style={{ cursor: "pointer" }}
                          color={
                            (hover || currentValue) > index ? "#ee4d2d" : ""
                          }
                          onClick={() => handleClick(index + 1)}
                          onMouseOver={() => handleMouseOver(index + 1)}
                          onMouseLeave={handleMouseLeave}
                        />
                      );
                    })}
                  </div>
                  <div class="form-outline">
                    <textarea
                      class="form-control"
                      id="textAreaExample"
                      value={content}
                      onChange={(e) => setContent(e.target.value)}
                    ></textarea>
                  </div>
                  <div>
                    <Button onClick={handleSubmitComment}>Bình luận</Button>
                  </div>
                </Col>
              </Row>
            </div>

            {comments &&
              comments.map((cmt) => {
                return (
                  <div className="border-bottom p-2">
                    {console.log(cmt)}
                    <Row>
                      <Col sm={1} className="text-center">
                        <img
                          src={cmt.usersId.avatar}
                          width={60}
                          height={60}
                          className="rounded-circle"
                        />
                      </Col>
                      <Col sm={11}>
                        <div>
                          <h6>{cmt.usersId.name}</h6>
                        </div>
                        <div>{cmt.content}</div>
                        <div className="d-flex gap-2">
                          Đánh giá: <StarRating averageRating={cmt.rate} />
                        </div>
                      </Col>
                    </Row>
                  </div>
                );
              })}
          </div>
        </div>
      </div>
    </>
  );
};

export default Food;
