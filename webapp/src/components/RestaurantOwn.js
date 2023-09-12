import React, { useContext, useEffect, useState } from "react";
import Apis, { endpoints } from "../configs/Apis";
import { MyUserContext } from "../App";
import {
  Alert,
  Button,
  Col,
  Container,
  Form,
  FormCheck,
  Row,
  Spinner,
  Table,
} from "react-bootstrap";
import StarRating from "./StarRating";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPeopleGroup, faSearch } from "@fortawesome/free-solid-svg-icons";
import {
  Link,
  useNavigate,
  useParams,
  useSearchParams,
} from "react-router-dom";
import { toast } from "react-toastify";

const RestaurantOwn = () => {
  const { id } = useParams();
  const [user, dispatch] = useContext(MyUserContext);
  const [restaurant, setRestaurant] = useState(null);
  const [evaluate, setEvaluate] = useState(0);
  const [foods, setFoods] = useState([]);
  const [foodSearch] = useSearchParams();
  const [kw, setKw] = useState("");
  const nav = useNavigate();
  let userId = user.id;
  const [error, setError] = useState({});
  const [orders, setOrders] = useState();
  const [follower, setFollower] = useState(0);

  useEffect(() => {
    if (user === null || user.role !== "ROLE_RESTAURANT") {
      return nav("/");
    }
  }, []);
  useEffect(() => {
    const process = async () => {
      let endpoint = endpoints.getRestaurantByUserId;
      await Apis.get(`${endpoint}${userId}`)
        .then((response) =>
          setRestaurant({
            ...restaurant,
            id: response.data.id,
            resName: response.data.name,
            deliveryFee: response.data.deliveryFee,
            status: response.data.status,
            userId: response.data.userId.id,
          })
        )
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    process();
  }, []);

  useEffect(() => {
    if (restaurant !== null) {
      const fetchEvaluate = async () => {
        let endpoint = endpoints.restaurants;
        await Apis.get(
          `${endpoint}${restaurant.id}/getAverageRateForRestaurant`
        )
          .then((response) => setEvaluate(response.data))
          .catch((error) => {
            console.error("Error fetching data:", error);
          });
      };
      fetchEvaluate();
    }
  }, [restaurant]);

  useEffect(() => {
    if (restaurant !== null) {
      const fetchFoods = async () => {
        try {
          let e = endpoints.foods;
          let foodName = foodSearch.get("kw");
          let e1 = `${e}${restaurant.id}`;
          if (foodName !== null) {
            e1 = `${e}${restaurant.id}?kw=${kw}`;
            console.log(e1);
          }

          const response = await Apis.get(e1);
          // console.log(response);

          setFoods(response.data);
        } catch (error) {
          console.log(error);
        }
      };
      fetchFoods();
    }
  }, [restaurant, foodSearch]);

  const UpdateRestaurant = async (e) => {
    e.preventDefault();
    // console.log(restaurant);

    const messageError = {};
    if (!restaurant.resName.trim()) {
      messageError.resName = "Tên nhà hàng không được để trống";
    } else if (
      restaurant.resName.length < 3 ||
      restaurant.resName.length > 45
    ) {
      messageError.resName = "Tên nhà hàng không hợp lệ (3 - 45 ký tự)";
    }
    if (!restaurant.deliveryFee.toString().trim()) {
      messageError.deliveryFee = "Phí vận chuyển không được để trống";
    } else if (
      restaurant.deliveryFee < 10000 ||
      restaurant.deliveryFee > 50000
    ) {
      messageError.deliveryFee =
        "Phí vận chuyển không hợp lệ (10000 - 50000 VND)";
    }
    setError(messageError);
    if (Object.keys(messageError).length === 0) {
      let endpoint = endpoints.restaurants;
      let res = await Apis.patch(
        `${endpoint}${restaurant.id}/updateRestaurant`,
        {
          id: restaurant.id,
          name: restaurant.resName,
          deliveryFee: restaurant.deliveryFee,
          status: restaurant.status,
          userId: restaurant.userId,
        }
      );

      if (res.status === 200) {
        toast.success("Cập nhật thành công");
      }
    }
  };

  useEffect(() => {
    const fetchOrders = async () => {
      let endpoint = endpoints.getOrderDetailByRestaurantId;
      let res = await Apis.get(`${endpoint}${restaurant.id}`);
      if (res.status === 200) {
        setOrders(res.data);
      }
    };
    if (restaurant !== null) {
      fetchOrders();
    }
  }, [restaurant]);

  useEffect(() => {
    const process = async () => {
      let endpoint = endpoints.follow;
      await Apis.get(`${endpoint}restaurant/${id}`).then((res) =>
        setFollower(res.data)
      );
    };
    process();
  }, []);

  if (restaurant === null) {
    return (
      <div className="text-center my-5">
        <Spinner animation="border" />
      </div>
    );
  }
  const handleSearch = (evt) => {
    evt.preventDefault();
    nav(`?kw=${kw}`);
  };
  return (
    <>
      <Container>
        <Row className="mt-3">
          <Col sm={4}>
            <img
              src={user.avatar}
              style={{ width: "400px", height: "300px", objectFit: "cover" }}
            />
          </Col>
          <Col sm={8}>
            <Form>
              <Form.Group className="mb-2">
                <Form.Label>Tên nhà hàng: </Form.Label>
                <Form.Control
                  value={restaurant.resName}
                  onChange={(e) =>
                    setRestaurant({ ...restaurant, resName: e.target.value })
                  }
                />
                {error.resName && (
                  <span className="text-danger">{error.resName}</span>
                )}
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Phí vận chuyển(VND): </Form.Label>
                <Form.Control
                  type="number"
                  value={restaurant.deliveryFee}
                  onChange={(e) =>
                    setRestaurant({
                      ...restaurant,
                      deliveryFee: e.target.value,
                    })
                  }
                />
                {error.deliveryFee && (
                  <span className="text-danger">{error.deliveryFee}</span>
                )}
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label className="me-2">Trạng thái: </Form.Label>
                {restaurant.status === 1 ? (
                  <span className="text-success">Đang hoạt động</span>
                ) : (
                  <span className="text-danger">
                    Không hoạt động {restaurant.status}
                  </span>
                )}
              </Form.Group>
              <Form.Group className="d-flex">
                <Form.Label className="mb-0 me-2">Đánh giá:</Form.Label>
                {<StarRating averageRating={evaluate} />}
              </Form.Group>

              <Form.Group className="my-2">
                <FontAwesomeIcon icon={faPeopleGroup} /> Số người theo dõi: {follower}
              </Form.Group>

              <Form.Group>
                <Button onClick={UpdateRestaurant}>Cập nhật</Button>
              </Form.Group>
            </Form>
          </Col>
        </Row>
        <Row className="mt-3 p-2">
          <Col sm={6}>
            <h3 className="bg-primary text-white mb-3 p-2 ps-3 flex-grow-1">
              Thực đơn
            </h3>
            <div className="mb-3 d-flex justify-content-between">
              <div>
                <Link to={`/restaurantOwn/${restaurant.id}/addFood/`}>
                  <Button>Thêm món ăn</Button>
                </Link>
              </div>
              <div className="d-flex border">
                <input
                  placeholder="Tìm món..."
                  style={{
                    backgroundColor: "transparent",
                    border: "none",
                    fontSize: "14px",
                    width: "100%",
                    paddingLeft: "14px",
                  }}
                  onChange={(e) => setKw(e.target.value)}
                  onFocus={(e) => (e.target.style.outline = "none")}
                />
                <Button style={{ borderRadius: 0 }} onClick={handleSearch}>
                  <FontAwesomeIcon icon={faSearch} />
                </Button>
              </div>
            </div>
            {foods.length === 0 ? (
              <>
                <Alert variant="danger" className="my-3 text-center">
                  Chưa có món ăn nào trong thực đơn
                </Alert>
              </>
            ) : (
              foods.map((f) => {
                return (
                  <Row className="mb-3" key={f.id}>
                    <Col sm={2}>
                      <Link to={`/restaurantOwn/foods/${f.id}`} key={f.id}>
                        <img
                          src={f.image}
                          style={{
                            width: "90px",
                            height: "90px",
                            objectFit: "cover",
                          }}
                        />
                      </Link>
                    </Col>
                    <Col sm={5}>
                      <div className="ms-2">
                        <div>
                          <Link
                            to={`/restaurantOwn/foods/${f.id}`}
                            key={f.id}
                            style={{ textDecoration: "none", color: "black" }}
                          >
                            <strong>{f.name}</strong>
                          </Link>
                        </div>
                        <div>Giá: {f.price} đồng</div>
                        <div>Loại món ăn: {f.foodType}</div>
                      </div>
                    </Col>
                    <Col sm={3}>
                      {f.available === 1 ? (
                        <div className="text-success">Còn hàng</div>
                      ) : (
                        <div className="text-danger">Hết hàng</div>
                      )}
                    </Col>
                    <Col sm={2}>
                      <Link
                        to={`/restaurantOwn/foods/${f.id}`}
                        key={f.id}
                        style={{ textDecoration: "none" }}
                      >
                        <Button>Chi tiết</Button>
                      </Link>
                    </Col>
                  </Row>
                );
              })
            )}
          </Col>
          <Col sm={6}>
            <h3 className="bg-primary text-white mb-3 p-2 ps-3 flex-grow-1">
              Đơn hàng
            </h3>
            <div>
              {orders &&
                orders.slice().reverse().map((order) => {
                  let totalAmount = 0;
                  order.orderDetailSet.forEach((item) => {
                    totalAmount += item.unitPrice * item.quantity;
                  });
                  return (
                    <div key={order.id} className="mb-3 border-bottom">
                      <Table striped bordered hover>
                        <colgroup>
                          <col style={{ width: "10%" }} />{" "}
                          <col style={{ width: "40%" }} />{" "}
                          <col style={{ width: "20%" }} />{" "}
                          <col style={{ width: "10%" }} />{" "}
                        </colgroup>
                        <thead>
                          <tr>
                            <th>Ảnh sản phẩm</th>
                            <th>Tên sản phẩm</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                          </tr>
                        </thead>
                        <tbody>
                          {order.orderDetailSet.map((item) => (
                            <tr key={item.id}>
                              <td>
                                <img
                                  src={item.foodsId.image}
                                  width={100}
                                  height={100}
                                />
                              </td>
                              <td>{item.foodsId.name}</td>
                              <td>{item.unitPrice} VND</td>
                              <td>{item.quantity}</td>
                            </tr>
                          ))}
                        </tbody>
                      </Table>
                      <div className="text-end mb-2">
                        Tổng tiền: {totalAmount}
                      </div>
                    </div>
                  );
                })}
            </div>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default RestaurantOwn;
