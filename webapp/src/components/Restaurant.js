import React, { useContext, useEffect, useState } from "react";
import { Alert, Button, Col, Container, Row, Spinner } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPeopleGroup, faSearch } from "@fortawesome/free-solid-svg-icons";
import {
  Link,
  useNavigate,
  useParams,
  useSearchParams,
} from "react-router-dom";
import Apis, { authApi, endpoints } from "../configs/Apis";
import StarRating from "./StarRating";
import { toast } from "react-toastify";
import { MyCartContext, MyUserContext } from "../App";

const Restaurant = () => {
  const [user, dispatch] = useContext(MyUserContext);
  const [cartCounter, cartDispatch] = useContext(MyCartContext);
  const { id } = useParams();
  const [restaurant, setRestaurant] = useState(null);
  const [foods, setFoods] = useState([]);
  const [follows, setFollows] = useState([]);
  const [foodSearch] = useSearchParams();
  const [kw, setKw] = useState("");
  const [evaluate, setEvaluate] = useState(0);
  const nav = useNavigate();
  const [isFollowing, setIsFollowing] = useState(
    follows.some((item) => item.restaurantsId.id === restaurant.id)
      ? true
      : false
  );
  const [follower, setFollower] = useState(0);


  useEffect(() => {
    const fetchRestaurantDetail = async () => {
      try {
        let e = endpoints.restaurants;
        const response = await Apis.get(`${e}${id}`);
        setRestaurant(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchRestaurantDetail();
  }, [id]);

  useEffect(() => {
    const fetchFoods = async () => {
      try {
        let e = endpoints.foods;
        let foodName = foodSearch.get("kw");
        let e1 = `${e}${id}`;
        if (foodName !== null) {
          e1 = `${e}${id}?kw=${kw}`;
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
  }, [foodSearch]);

  useEffect(() => {
    const fetchEvaluate = async () => {
      let endpoint = endpoints.restaurants;
      await Apis.get(`${endpoint}${id}/getAverageRateForRestaurant`)
        .then((response) => setEvaluate(response.data))
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    fetchEvaluate();
  }, [id]);

  useEffect(() => {
    const processFollow = async () => {
      let endpoint = endpoints.follow;
      await Apis.get(`${endpoint}${user.id}`)
        .then((res) => {
          setFollows(res.data);
        })
        .catch((error) => console.log(error));
    };
    if (user) {
      processFollow();
    }
  }, []);

  useEffect(() => {
    setIsFollowing(
      follows.some((item) => item.restaurantsId.id === restaurant.id)
        ? true
        : false
    );
  }, [follows.length]);

  useEffect(() => {
    const process = async () => {
      let endpoint = endpoints.follow;
      await Apis.get(`${endpoint}restaurant/${id}`).then((res) =>
        setFollower(res.data)
      );
    };
    process();
  }, [isFollowing]);

  const handleFollow = async (e) => {
    e.preventDefault();
    if (user === null || user.role === "ROLE_RESTAURANT") {
      return nav("/login");
    }
    let endpoint = endpoints.follow;
    await authApi()
      .post(`${endpoint}${restaurant.id}`)
      .then((res) => {
        if (res.status === 200) {
          setIsFollowing(true);
          toast.success("Theo dõi thành công");
        }
      })
      .catch((error) => toast.error(error));
    console.log(isFollowing);
  };

  const handleUnFollow = async (e) => {
    e.preventDefault();
    if (user === null || user.role === "ROLE_RESTAURANT") {
      return nav("/login");
    }
    let endpoint = endpoints.follow;
    await authApi()
      .delete(`${endpoint}${restaurant.id}`)
      .then((res) => {
        if (res.status === 204) {
          setIsFollowing(false);
          toast.success("Bỏ theo dõi thành công");
        }
      })
      .catch((error) => toast.error(error));
    console.log(isFollowing);
  };

  const order = (food) => {
    if (restaurant !== null) {
      cartDispatch({
        type: "inc",
        payload: 1,
      });
      let cart = JSON.parse(localStorage.getItem("cart") || null);
      if (cart == null) {
        cart = {};
      }
      if (food.id in cart) {
        cart[food.id]["quantity"] += 1;
      } else {
        cart[food.id] = {
          id: food.id,
          name: food.name,
          quantity: 1,
          price: food.price,
          restaurantId: restaurant,
        };
      }
      localStorage.setItem("cart", JSON.stringify(cart));
      toast.success("Thêm vào giỏ hàng thành công");
    }
  };

  if (!restaurant) {
    return <Spinner animation="border" />;
  }

  const handleSearch = (evt) => {
    evt.preventDefault();
    nav(`?kw=${kw}`);
  };
  return (
    <>
      <div style={{ position: "relative" }}>
        <Container>
          <div className="d-flex mx-4 gap-3 p-3">
            <div style={{ width: 400, height: 250 }}>
              <img
                alt="Hình cửa hàng"
                style={{ width: 400, height: 250, objectFit: "cover" }}
                src={restaurant.userId.avatar}
              />
            </div>
            <div
              style={{
                flex: 1,
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
              }}
            >
              <h4>{restaurant.name}</h4>
              <div style={{ fontSize: 14 }}>
                Địa chỉ nhà hàng: {restaurant.userId.address}
              </div>
              {<StarRating averageRating={evaluate} />}
              <div className="my-2">
                Phí vận chuyển: {restaurant.deliveryFee} VND
              </div>
              <div>
                <FontAwesomeIcon icon={faPeopleGroup} /> Số người theo dõi:{" "}
                {follower}
              </div>
              <div className="my-2">
                {isFollowing ? (
                  <Button onClick={handleUnFollow}>Đang theo dõi</Button>
                ) : (
                  <Button onClick={handleFollow}>Theo dõi</Button>
                )}
              </div>
            </div>
          </div>
        </Container>

        <div
          style={{
            backgroundColor: "#eee",
            width: "100%",
            display: "flex",
            justifyContent: "center",
          }}
        >
          {/* Search */}
          <div
            style={{
              width: "75%",
              backgroundColor: "#fff",
              marginTop: 20,
              marginBottom: 20,
            }}
          >
            <div className="d-flex flex-column align-items-center px-5">
              <div style={styles.intputWrapper}>
                <input
                  placeholder="Tìm món..."
                  style={styles.inputSearch}
                  onChange={(e) => setKw(e.target.value)}
                  onFocus={(e) => (e.target.style.outline = "none")}
                  onBlur={(e) => (e.target.style.outline = "")}
                />
                <Button variant="" onClick={handleSearch}>
                  <FontAwesomeIcon icon={faSearch} />
                </Button>
              </div>

              <div className="w-100">
                {foods.length === 0 && (
                  <Alert variant="danger" className="my-3">
                    Không có sản phẩm nào!
                  </Alert>
                )}
                {foods.map((f) => {
                  return (
                    <Row className="my-4" key={f.id}>
                      <Col sm={2}>
                        <img
                          alt="Hình món ăn"
                          style={styles.imgFood}
                          src={f.image}
                        />
                      </Col>
                      <Col sm={7}>
                        <h4 style={styles.textFoodName}>{f.name}</h4>
                        <div className="fw-bold" style={{ color: "#0288d1" }}>
                          {f.price}
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
                        </div>
                        <div
                          className={
                            f.available === 0 ? "text-danger" : "text-success"
                          }
                        >
                          {f.available === 0 ? "Hết hàng" : "Còn hàng"}
                        </div>
                        <div>Loại thức ăn: Món nước</div>
                      </Col>
                      <Col
                        sm={3}
                        className="justify-content-center d-flex flex-column gap-2 px-5"
                      >
                        <Button
                          variant="none"
                          style={{
                            padding: 5,
                            backgroundColor: "#ee4d2d",
                            color: "white",
                            fontWeight: 400,
                          }}
                          onClick={() => order(f)}
                        >
                          Thêm vào giỏ hàng
                        </Button>
                      </Col>
                    </Row>
                  );
                })}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
const styles = {
  comments: {
    display: "flex",
    gap: 10,
    background: "rgb(197 157 157)",
    width: 500,
    borderRadius: 50,
    padding: 20,
    margin: "auto",
  },
  intputWrapper: {
    backgroundColor: "#fff",
    borderRadius: 10,
    boxShadow: "0px 0px 8px #ddd",
    display: "flex",
    alignItems: "center",
    width: "60%",
    padding: "0 15px",
    marginTop: 20,
  },
  inputSearch: {
    backgroundColor: "transparent",
    border: "none",
    fontSize: "16px",
    width: "100%",
    padding: "10px",
  },
  star: {
    display: "flex",
    listStyle: "none",
    // color: "#ffc107",
    marginRight: 5,
    alignItems: "center",
  },
  textFoodName: {
    overflow: "hidden",
    textOverflow: "ellipsis",
    whiteSpace: "nowrap",
    cursor: "pointer",
    textDecoration: "none",
  },
  imgFood: {
    width: 100,
    height: 100,
    objectFit: "cover",
    display: "block",
    margin: "0 auto",
    cursor: "pointer",
  },
};
export default Restaurant;
