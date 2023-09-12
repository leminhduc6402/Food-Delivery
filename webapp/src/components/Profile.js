import React, { useContext, useEffect, useRef, useState } from "react";
import { MyUserContext } from "../App";
import {
  Alert,
  Button,
  Col,
  Container,
  Form,
  Row,
  Spinner,
  Table,
} from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { toast } from "react-toastify";
import { Link, useNavigate, useParams } from "react-router-dom";

const Profile = () => {
  const { id } = useParams();
  const [user, dispatch] = useContext(MyUserContext);
  const [previewImage, setPreviewImage] = useState(null);
  const nav = useNavigate();
  const [error, setError] = useState({});
  const [isLoading, setIsLoading] = useState(true);
  const [profile, setProfile] = useState({
    id: "",
    name: "",
    phone: "",
    cccd: "",
    email: "",
    birth: user.birth,
    gender: "",
    address: "",
    avatar: "",
  });
  const [orders, setOrders] = useState();
  const avatar = useRef(null);
  if (user === null) {
    nav("/login");
  }

  useEffect(() => {
    const fetchProfile = async () => {
      let endpoint = endpoints.register;
      await Apis.get(`${endpoint}${id}`)
        .then((res) =>
          setProfile((preValues) => ({
            ...preValues,
            id: res.data.id,
            name: res.data.name,
            phone: res.data.phone,
            cccd: res.data.cccd,
            email: res.data.email,
            birth: res.data.birth,
            gender: res.data.gender,
            address: res.data.address,
            avatar: res.data.avatar,
          }))
        )
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    fetchProfile();
  }, [id]);
  const change = (evt, field) => {
    setProfile((current) => {
      return { ...current, [field]: evt.target.value };
    });
  };

  const handleUpdateProfile = async (e) => {
    e.preventDefault();

    const processUser = async () => {
      let form = new FormData();
      for (let field in profile) {
        if (field !== "avatar") {
          form.append(field, profile[field]);
        }
      }
      form.append("avatar", avatar.current.files[0] || profile.avatar);

      let endpoint = endpoints.register;
      let res = await Apis.post(`${endpoint}${id}`, form);
      if (res.status === 200) {
        toast.success("Cập nhật thành công");
      }
    };
    const messageError = {};
    if (!profile.name.trim()) {
      messageError.name = "Họ và tên không được bỏ trống";
    } else if (profile.name.length < 3) {
      messageError.name = "Họ và tên tối thiểu 3 ký tự";
    }

    if (!profile.phone.trim()) {
      messageError.phone = "Số điện thoại không được để trống";
    } else if (profile.phone.length !== 10) {
      messageError.phone = "Số điện thoại phải đúng 10 ký tự";
    }

    if (profile.cccd.length !== 12) {
      messageError.cccd = "Căn cước công dân phải đủ 12 ký tự";
    }

    if (!profile.email.trim()) {
      messageError.email = "Địa chỉ email không được để trống";
    } else if (!validateEmail(profile.email)) {
      messageError.email = "Vui lòng nhập đúng định dạng email";
    }

    if (!profile.address.trim()) {
      messageError.gender = "Địa chỉ không được để trống";
    } else if (profile.address.length < 40) {
      messageError.address = "Vui lòng nhập địa chỉ chi tiết";
    }
    setError(messageError);

    if (Object.keys(messageError).length === 0) {
      await processUser();
    }
  };
  const validateEmail = (email) => {
    // Biểu thức chính quy để kiểm tra địa chỉ email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
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

  useEffect(() => {
    const fetchOrders = async () => {
      let endpoint = endpoints.getOrderDetailByUserId;
      let res = await Apis.get(`${endpoint}${id}`);
      if (res.status === 200) {
        setOrders(res.data);
        setIsLoading(false);
      }
    };
    fetchOrders();
  }, [id]);
  if (isLoading) {
    return <Spinner animation="border" />;
  }
  return (
    <>
      <Container className="mt-3">
        <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
          <Row>
            <Col sm={3}>
              <div className="d-flex flex-column align-items-center justify-content-center">
                <img
                  className="rounded-circle shadow"
                  src={previewImage || profile.avatar}
                  style={{ width: 190, height: 190 }}
                  alt="Ảnh tạm thời"
                />
                <label
                  htmlFor="avatar"
                  type="button"
                  className="btn btn-secondary mt-3"
                >
                  Chọn ảnh
                </label>
                <Form.Control
                  id="avatar"
                  type="file"
                  style={{ display: "none" }}
                  onChange={handleImageChange}
                  ref={avatar}
                  accept=".jpg,.jpeg,.png"
                />
              </div>
            </Col>

            <Col sm={9}>
              <div>
                <h3 className="bg-primary bg-gradient text-white p-3">
                  Thông tin cá nhân
                </h3>
                <div>
                  <Form>
                    <Form.Group className="mb-3">
                      <Form.Label>Họ và tên:</Form.Label>
                      <Form.Control
                        value={profile.name}
                        onChange={(e) => change(e, "name")}
                      />
                      {error.name && (
                        <span className="text-danger">{error.name}</span>
                      )}
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Số điện thoại:</Form.Label>
                      <Form.Control
                        type="number"
                        value={profile.phone}
                        onChange={(e) => change(e, "phone")}
                      />
                      {error.phone && (
                        <span className="text-danger">{error.phone}</span>
                      )}
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Căn cước công dân:</Form.Label>
                      <Form.Control
                        value={profile.cccd}
                        onChange={(e) => change(e, "cccd")}
                      />
                      {error.cccd && (
                        <span className="text-danger">{error.cccd}</span>
                      )}
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Email:</Form.Label>
                      <Form.Control
                        value={profile.email}
                        onChange={(e) => change(e, "email")}
                      />
                      {error.email && (
                        <span className="text-danger">{error.email}</span>
                      )}
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Ngày sinh:</Form.Label>
                      <Form.Control
                        type="date"
                        value={
                          profile.birth
                            ? new Date(profile.birth).toLocaleDateString(
                                "en-CA"
                              )
                            : profile.birth
                        }
                        onChange={(e) => change(e, "birth")}
                      />
                      {error.birth && (
                        <span className="text-danger">{error.birth}</span>
                      )}
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Giới tính:</Form.Label>
                      <Form.Select
                        value={profile.gender === "1" ? "1" : "0"}
                        onChange={(e) => change(e, "gender")}
                      >
                        <option value="1">Nam</option>
                        <option value="0">Nữ</option>
                      </Form.Select>
                      {error.gender && (
                        <span className="text-danger">{error.gender}</span>
                      )}
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Địa chỉ:</Form.Label>
                      <Form.Control
                        value={profile.address}
                        onChange={(e) => change(e, "address")}
                      />
                      {error.address && (
                        <span className="text-danger">{error.address}</span>
                      )}
                    </Form.Group>

                    <Form.Group className="mb-3 d-flex justify-content-end">
                      <Button onClick={handleUpdateProfile}>Cập nhật</Button>
                    </Form.Group>
                  </Form>
                </div>
              </div>
            </Col>
          </Row>
        </div>
        <div className="shadow-lg p-3 mb-3 bg-body rounded gap-3">
          <h3 className="bg-primary bg-gradient text-white p-3">
            Lịch sử mua hàng
          </h3>

          <div>
            {orders &&
              orders.map((order) => {
                let totalAmount = 0; // Khởi tạo tổng tiền của đơn hàng
                order.orderDetailSet.forEach((item) => {
                  totalAmount += item.unitPrice * item.quantity; // Tính tổng tiền của đơn hàng
                });

                return (
                  <div key={order.id} className="mb-3 border-bottom">
                    <Table striped bordered hover>
                      <colgroup>
                        <col style={{ width: "10%" }} />{" "}
                        <col style={{ width: "40%" }} />{" "}
                        <col style={{ width: "20%" }} />{" "}
                        <col style={{ width: "10%" }} />{" "}
                        <col style={{ width: "20%" }} />{" "}
                      </colgroup>
                      <thead>
                        <tr>
                          <th>Ảnh sản phẩm</th>
                          <th>Tên sản phẩm</th>
                          <th>Giá</th>
                          <th>Số lượng</th>
                          <th>Đánh giá</th>
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
                            <td>
                              <Link to={`/foods/${item.foodsId.id}`}>
                                <Button>Đánh giá</Button>
                              </Link>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </Table>
                    <div className="text-end mb-2">Tổng tiền: {totalAmount}</div>
                  </div>
                );
              })}
          </div>
        </div>
      </Container>
    </>
  );
};

export default Profile;
