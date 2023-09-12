import {
  Badge,
  Button,
  Col,
  Container,
  Form,
  Nav,
  Navbar,
  Row,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch, faShoppingCart } from "@fortawesome/free-solid-svg-icons";
import { Link, useNavigate } from "react-router-dom";
import { useContext, useState } from "react";
import { MyCartContext, MyUserContext } from "../App";

const Header = () => {
  const [user, dispatch] = useContext(MyUserContext);
  const [cartCounter] = useContext(MyCartContext);
  const nav = useNavigate();
  const [kw, setKw] = useState("");

  const search = (e) => {
    // nav("/");

    e.preventDefault();
    nav(`/?kw=${kw}`);
  };
  const logout = () => {
    dispatch({
      type: "logout",
    });
    nav("/");
  };
  return (
    <>
      <Navbar expand="lg" style={{ backgroundColor: "#64c5b1" }} className="">
        <Container>
          <Navbar.Brand
            href="/"
            className="text-white"
            style={{ fontWeight: 800 }}
          >
            Nomin
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="flex-grow-1">
              {user && user.role === "ROLE_RESTAURANT" ? (
                <div className="text-white d-flex align-items-center">
                  <Link
                    to={`/restaurantOwn/${user.id}`}
                    className="text-white d-flex align-items-center"
                    style={{
                      fontWeight: 500,
                      textDecoration: "none",
                      padding: 8,
                    }}
                  >
                    Cửa hàng của bạn
                  </Link>
                  <Link
                    to={`/restaurantOwn/stats/${user.id}`}
                    className="text-white d-flex align-items-center"
                    style={{
                      fontWeight: 500,
                      textDecoration: "none",
                      padding: 8,
                    }}
                  >
                    Thống kê
                  </Link>
                </div>
              ) : (
                <div className="d-flex">
                  <Nav.Link
                    href="/"
                    className="text-white"
                    style={{ fontWeight: 500 }}
                  >
                    Trang chủ
                  </Nav.Link>
                  <Link
                    to="/cart"
                    className="text-white d-flex align-items-center"
                    style={{
                      fontWeight: 500,
                      textDecoration: "none",
                      padding: 8,
                      position: "relative",
                    }}
                  >
                    Giỏ hàng
                    <FontAwesomeIcon
                      className="mx-1"
                      fontSize={20}
                      icon={faShoppingCart}
                    />
                    <Badge
                      className="pe-2 ps-2"
                      style={{
                        position: "absolute",
                        fontSize: "10px",
                        bottom: "56%",
                        left: "82%",
                        color: "#64c5b1",
                        background: "#fff",
                        padding: 2,
                      }}
                      bg=""
                      pill
                    >
                      {cartCounter}
                    </Badge>
                  </Link>
                </div>
              )}
              <Form className="w-50 m-auto">
                <Row>
                  <Col className="">
                    <Form
                      className="d-flex position-relative"
                      onSubmit={search}
                    >
                      <Form.Control
                        type="text"
                        value={kw}
                        placeholder="Nhập tên cửa hàng...."
                        className="rounded-pill"
                        onChange={(e) => setKw(e.target.value)}
                        style={{ fontSize: "12px", padding: 9 }}
                        name="kw"
                        aria-label="Search"
                      />
                      <Button
                        type="submit"
                        variant="outline-white position-absolute end-0 rounded-pill"
                      >
                        <FontAwesomeIcon icon={faSearch} />
                      </Button>
                    </Form>
                  </Col>
                </Row>
              </Form>
            </Nav>
            <Nav>
              {user === null ? (
                <>
                  <Link className="nav-link text-white" to="/login">
                    Đăng nhập
                  </Link>
                  <Link className="nav-link text-white" to="/register">
                    Đăng ký
                  </Link>
                </>
              ) : (
                <>
                  <img
                    alt="Ảnh đại diện"
                    src={user.avatar}
                    width={40}
                    height={40}
                    className="rounded-circle"
                  />
                  <Link
                    className="nav-link text-white"
                    to={`/profile/${user.id}`}
                  >
                    {user.name}
                  </Link>
                  <Button
                    variant="secondary"
                    className="text-danger"
                    onClick={logout}
                  >
                    Đăng xuất
                  </Button>
                </>
              )}
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>
  );
};
export default Header;
