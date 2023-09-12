import { useContext, useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import cookie from "react-cookies";
import { Navigate, useNavigate, useSearchParams } from "react-router-dom";
import { MyUserContext } from "../App";
import Apis, { authApi, endpoints } from "../configs/Apis";

const Login = () => {
  const [user, dispatch] = useContext(MyUserContext);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [q] = useSearchParams();
  const nav = useNavigate();
  if (cookie.load('token')) {
    nav('/')
  }
  const login = (evt) => {
    evt.preventDefault();

    const process = async () => {
      try {
        let res = await Apis.post(endpoints["login"], {
          username: username,
          password: password,
        });
        cookie.save("token", res.data);

        let {data} = await authApi().get(endpoints.getCurrentUser);
        // console.log(data);
        
        cookie.save("user", data);

        dispatch({
          type: "login",
          payload: data,
        });
      } catch (err) {
        console.error(err);
      }
    };
    process();
  };
  
  if (user !== null) {
    if (user.role === "ROLE_RESTAURANT") {
      return nav(`/restaurantOwn/${user.id}`)
    }
    let next = q.get("next") || "/";
    return <Navigate to={next} />;
  }

  return (
    <>
      <h1 className="text-center text-info mt-3">ĐĂNG NHẬP NGƯỜI DÙNG</h1>
      <Container>
        <Form onSubmit={login}>
          <Form.Group className="mb-3">
            <Form.Label>Tên đăng nhập</Form.Label>
            <Form.Control
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              type="text"
              placeholder="Tên đăng nhập"
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Mật khẩu</Form.Label>
            <Form.Control
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              type="password"
              placeholder="Mật khẩu"
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Button variant="primary" type="submit">
              Đăng nhập
            </Button>
          </Form.Group>
        </Form>
      </Container>
    </>
  );
};

export default Login;
