import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";
import { Alert, Card, Container, Spinner } from "react-bootstrap";

const Home = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [hovered, setHovered] = useState(null);
  const [btnSearch] = useSearchParams();

  useEffect(() => {
    const loadRestaurans = async () => {
      try {
        let e = endpoints.restaurants;
        let kw = btnSearch.get("kw");
        if (kw !== null) {
          e = `${e}?kw=${kw}`;
        }
        let res = await Apis.get(e);
        setRestaurants(res.data);
      } catch (error) {
        console.log(error);
      }
    };
    loadRestaurans();
  }, [btnSearch]);

  // if (restaurants.length === 0) {
  //   return (
  //     <div className="text-center my-5">
  //       <Spinner animation="border" />
  //     </div>
  //   );
  // }

  const handleMouseHover = (restaurantId) => {
    setHovered(restaurantId);
  };

  const handleMouseLeave = () => {
    setHovered(null);
  };
  return (
    <>
      {restaurants.length === 0 && (
        <Alert variant="danger" className="my-3 mx-5">
          Không có cửa hàng nào!
        </Alert>
      )}
      <Container className="d-flex my-3 ">
        <div className="d-flex flex-wrap gap-3 p-2">
          {restaurants.map((res) => {
            if (res.status === 1) {
              return (
                <Link
                  to={`/restaurants/${res.id}`}
                  key={res.id}
                  style={{ textDecoration: "none" }}
                >
                  <Card
                    style={{
                      width: "200px",
                      boxShadow:
                        hovered === res.id
                          ? "1px 2px 9px #9A989A"
                          : "1px 2px 9px #E6E6E6",
                    }}
                    border="light"
                    key={res.id}
                    onMouseEnter={() => handleMouseHover(res.id)}
                    onMouseLeave={() => handleMouseLeave(res.id)}
                  >
                    <Card.Img
                      variant="top"
                      src={res.userId.avatar}
                      style={{
                        height: "200px",
                        objectFit: "cover",
                        borderEndStartRadius: "none",
                      }}
                    />
                    <Card.Body className="py-1 px-2">
                      <Card.Title
                        style={{
                          overflow: "hidden",
                          textOverflow: "ellipsis",
                          whiteSpace: "nowrap",
                          fontSize: "14px",
                          fontWeight: "700",
                        }}
                      >
                        {res.name}
                      </Card.Title>
                      <Card.Title
                        style={{
                          overflow: "hidden",
                          textOverflow: "ellipsis",
                          whiteSpace: "nowrap",
                          fontSize: "12px",
                          fontWeight: "200",
                        }}
                      >
                        {res.userId.address}
                      </Card.Title>
                    </Card.Body>
                    {/* <hr className="m-0"/> */}
                  </Card>
                </Link>
              );
            }
          })}
        </div>
      </Container>
    </>
  );
};
export default Home;
