import React, { useEffect, useRef, useState } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const Register = () => {
  const [provinces, setProvinces] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [wards, setWards] = useState([]);
  const [provinceId, setProvinceId] = useState();
  const [districtId, setDistrictId] = useState();

  const [province, setProvince] = useState("");
  const [district, setDistrict] = useState("");
  const [ward, setWard] = useState("");

  const [detailAddress, setDetailAddress] = useState("");
  const [isRestaurant, setIsRestaurant] = useState(false);
  const [registerStatus, setRegisterStatus] = useState(false);
  const [error, setError] = useState({});
  
  const nav = useNavigate();

  const avatar = useRef(null);
  const defaultAvatarURL =
  "https://res.cloudinary.com/dvyk1ajlj/image/upload/v1690620249/xofbyivlqlztrhwlz08u.png"; // Đường dẫn hình ảnh mặc định từ Cloudinary
  const [user, setUser] = useState({
    id: "",
    name: "",
    phone: "",
    birth: "",
    gender: "",
    role: "",
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [restaurant, setRestaurant] = useState({
    resName: "",
    deliveryFee: "",
  });

  useEffect(() => {
    const fetchProvinces = async () => {
      const endpoint = endpoints.provinces;
      await Apis.get(endpoint)
        .then((response) => {
          setProvinces(response?.data?.results || []);
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    fetchProvinces();
  }, []);

  useEffect(() => {
    const fetchDistrict = async () => {
      const endpoint = endpoints.districts;
      await Apis.get(`${endpoint}${provinceId}`)
        .then((response) => {
          setDistricts(response?.data?.results || []);
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    fetchDistrict();
  }, [provinceId]);

  useEffect(() => {
    const fetchWard = async () => {
      const endpoint = endpoints.wards;
      await Apis.get(`${endpoint}${districtId}`)
        .then((response) => {
          setWards(response?.data?.results || []); // { resullts:[ {}, {}, {}, {} ] }
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    };
    fetchWard();
  }, [districtId]);

  const handleOnChangeProvince = (e) => {
    setProvinceId(e.target.value);
    setProvince(e.target.options[e.target.selectedIndex].textContent);
  };

  const handleOnChangeDistrict = (e) => {
    setDistrictId(e.target.value);
    setDistrict(e.target.options[e.target.selectedIndex].textContent);
  };
  const handleOnChangeWard = (e) => {
    setWard(e.target.options[e.target.selectedIndex].textContent);
  };

  const fullAddress = `${detailAddress}, ${ward}, ${district}, ${province}`;

  const change = (evt, field) => {
    setUser((current) => {
      return { ...current, [field]: evt.target.value };
    });
  };

  const changeRes = (evt, field) => {
    setRestaurant((current) => {
      return { ...current, [field]: evt.target.value };
    });
  };

  const register = async (e) => {
    e.preventDefault();
    let userId;
    
    const processUser = async () => {
      let form = new FormData();
      for (let field in user) {
        if (field !== "confirmPassword") {
          form.append(field, user[field]);
        }
      }
      form.append("avatar", avatar.current.files[0] || defaultAvatarURL);
      form.append("address", fullAddress);
      // console.log(endpoints.register);
      let res = await Apis.post(endpoints.register, form);
      userId = res.data.id;
    };

    const processRes = async () => {
      if (user.role === "ROLE_RESTAURANT") {
        let formRes = new FormData();
        for (let field in restaurant) {
          formRes.append(field, restaurant[field]);
        }
        formRes.append("avatar", avatar.current.files[0]);
        // Sử dụng biến userId để thiết lập trường userId của nhà hàng
        await formRes.append("userId", userId);
        // Tiến hành đăng ký nhà hàng
        await Apis.post(endpoints.restaurants, formRes);
      }
    };

    const messageError = {};
    if (!user.name.trim()) {
      messageError.name = "Họ tên không được bỏ trống";
    } else if (user.name.length < 3) {
      messageError.name = "Họ tên tối thiểu 3 ký tự"
    }

    if (!user.phone.trim()) {
      messageError.phone = "Số điện thoại không được bỏ trống";
    } else if (user.phone.length !== 10) {
      messageError.phone = "Số điện thoại phải nhập 10 ký tự";
    }

    if (user.email && !user.email.trim()) {
      messageError.email = "Email không được bỏ trống";
    } else {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(user.email)) {
        messageError.email = "Email không hợp lệ";
      }
    }

    if (!user.birth.trim()) {
      messageError.birth = "Vui lòng chọn ngày sinh";
    }

    if (!user.gender.trim()) {
      messageError.gender = "Vui lòng chọn giới tính";
    }
    
    if (fullAddress.length < 40) {
      messageError.address = "Vui lòng nhập địa chỉ chi tiết"
    }

    if (!user.role.trim()) {
      messageError.role = "Vui lòng chọn vai trò"
    }

    if (!user.username.trim()) {
      messageError.username = "Tài khoản không được bỏ trống"
    } else if (user.username.length < 5) {
      messageError.username = "Tài khoản tối thiểu 5 ký tự"
    }

    if (!user.password.trim()) {
      messageError.password = "Mật khẩu không được bỏ trống"
    } else if (user.password.length < 5) {
      messageError.password = "Mật khẩu tối thiểu 5 ký tự"
    }
    
    if (user.confirmPassword !== user.password) {
      messageError.confirmPassword = "Mật khẩu không trùng khớp"
    } 

    if (user.role === "ROLE_RESTAURANT") {
      if (!restaurant.resName.trim()) {
        messageError.resName = "Tên nhà hàng không được để trống"
      } else if (restaurant.resName < 3) {
        messageError.resName = "Tên nhà hàng tối thiểu 3 ký tự"
      }
      if (!restaurant.deliveryFee.trim()) {
        messageError.deliveryFee = "Phí ship không được để trống"
      } else if (restaurant.deliveryFee < 10000 && restaurant.deliveryFee > 50000) {
        messageError.deliveryFee = "Phí vận chuyển không hợp lệ"
      }
    }
    
    setError(messageError)

    if (Object.keys(messageError).length === 0) {
      setRegisterStatus(true);
      await processUser();

      if (user.role === "ROLE_RESTAURANT") {
        await processRes();
      }
      toast("Đăng ký thành công");
      nav("/login")
    }
  };
  return (
    <>
      <h1 className="text-center text-info mt-3">ĐĂNG KÝ NGƯỜI DÙNG</h1>

      <div className="container">
        <Form onSubmit={register}>
          <Form.Group className="mb-3">
            <Form.Label>
              Họ và tên(<span className="text-danger">*</span>):
            </Form.Label>
            <Form.Control
              type="text"
              onChange={(e) => change(e, "name")}
              placeholder="Nhập tên đầy đủ"
            />
            {error.name && <span className="text-danger">{error.name}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>
              Số điện thoại(<span className="text-danger">*</span>):
            </Form.Label>
            <Form.Control
              type="number"
              onChange={(e) => change(e, "phone")}
              placeholder="Nhập số điện thoại"
            />
            {error.phone && <span className="text-danger">{error.phone}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>
              Email(<span className="text-danger">*</span>):
            </Form.Label>
            <Form.Control
              type="text"
              onChange={(e) => change(e, "email")}
              placeholder="Nhập địa chỉ email"
            />
            {error.email && <span className="text-danger">{error.email}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>
              Ngày sinh(<span className="text-danger">*</span>):
            </Form.Label>
            <Form.Control
              type="date"
              onChange={(e) => change(e, "birth")}
              placeholder="Ngày sinh"
            />
            {error.birth && <span className="text-danger">{error.birth}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Giới tính:</Form.Label>
            <Form.Select
              aria-label="Default select example"
              onChange={(e) => change(e, "gender")}
            >
              <option>Chọn giới tính</option>
              <option value="1">Nam</option>
              <option value="0">Nữ</option>
            </Form.Select>
            {error.gender && <span className="text-danger">{error.gender}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>
              Địa chỉ(<span className="text-danger">*</span>):
            </Form.Label>

            <Form.Group className="d-flex gap-3">
              <Form.Select onChange={handleOnChangeProvince}>
                <option>--- Chọn Tỉnh/Thành phố ---</option>
                {provinces.map((pv) => {
                  return (
                    <option key={pv.province_id} value={pv.province_id}>
                      {pv.province_name}
                    </option>
                  );
                })}
              </Form.Select>

              <Form.Select onChange={handleOnChangeDistrict}>
                <option>--- Chọn Quận/Huyện ---</option>
                {districts.map((dis) => {
                  return (
                    <option key={dis.district_id} value={dis.district_id}>
                      {dis.district_name}
                    </option>
                  );
                })}
              </Form.Select>

              <Form.Select onChange={handleOnChangeWard}>
                <option>--- Chọn Xã/Phường ---</option>
                {wards.map((w) => {
                  return (
                    <option key={w.ward_id} value={w.ward_id}>
                      {w.ward_name}
                    </option>
                  );
                })}
              </Form.Select>
            </Form.Group>

            <Form.Group className="my-3">
              <Form.Label>
                Địa chỉ chi tiết(<span className="text-danger">*</span>):
              </Form.Label>
              <Form.Control
                type="text"
                placeholder="Số nhà, đường..."
                onChange={(e) => setDetailAddress(e.target.value)}
              />
            </Form.Group>
            <Form.Label>Địa chỉ chính thức:</Form.Label>
            <Form.Control
              type="text"
              onChange={(e) => change(e.target.value, "address")}
              value={fullAddress}
            />
            {error.address && <span className="text-danger">{error.address}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Vai trò:</Form.Label>
            <Form.Select
              aria-label="Default select example"
              onChange={(e) => {
                change(e, "role");
                {
                  e.target.value === "ROLE_RESTAURANT"
                    ? setIsRestaurant(true)
                    : setIsRestaurant(false);
                }
              }}
            >
              <option>--- Chọn vai trò người dùng ---</option>
              <option value="ROLE_USER">Người dùng cá nhân</option>
              <option value="ROLE_RESTAURANT">Cửa hàng</option>
            </Form.Select>
            {error.role && <span className="text-danger">{error.role}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>
              Tài khoản(<span className="text-danger">*</span>):
            </Form.Label>
            <Form.Control
              type="text"
              onChange={(e) => change(e, "username")}
              placeholder="Tên đăng nhập..."
            />
            {error.username && <span className="text-danger">{error.username}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>
              Mật khẩu(<span className="text-danger">*</span>):
            </Form.Label>
            <Form.Control
              type="password"
              onChange={(e) => change(e, "password")}
              placeholder="Mật khẩu..."
            />
            {error.password && <span className="text-danger">{error.password}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>
              Xác nhận mật khẩu(<span className="text-danger">*</span>):
            </Form.Label>
            <Form.Control
              type="password"
              onChange={(e) => change(e, "confirmPassword")}
              placeholder="Xác nhận mật khẩu..."
            />
            {error.confirmPassword && <span className="text-danger">{error.confirmPassword}</span>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Ảnh đại diện</Form.Label>
            <Form.Control type="file" ref={avatar} accept=".jpg,.jpeg,.png" />
          </Form.Group>
          {isRestaurant === true ? (
            <>
              <h1 className="text-center text-info mt-3">
                Thông tin nhà hàng của bạn
              </h1>
              <div className="container">
                <Form>
                  <Form.Group className="mb-3">
                    <Form.Label>Tên nhà hàng:</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Nhập tên cửa hàng..."
                      onChange={(e) => changeRes(e, "resName")}
                    />
                    {error.resName && <span className="text-danger">{error.resName}</span>}
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label>Phí vận chuyển:</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Nhập phí vận chuyển..."
                      onChange={(e) => {
                        changeRes(e, "deliveryFee");
                      }}
                    />
                    {error.deliveryFee && <span className="text-danger">{error.deliveryFee}</span>}
                  </Form.Group>
                </Form>
              </div>
            </>
          ) : (
            <div></div>
          )}
          <Form.Group className="mb-3">
            <Button variant="primary" type="submit">
              Đăng ký
            </Button>
          </Form.Group>
        </Form>
      </div>
    </>
  );
};

export default Register;
