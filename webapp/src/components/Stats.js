import React, { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { MyCartContext } from "../App";
import Apis, { endpoints } from "../configs/Apis";
import { Container, Form } from "react-bootstrap";
import { Chart } from "chart.js";
import ChartStast from "./ChartStast";

const Stats = () => {
  const { id } = useParams();
  const [user, dispatch] = useContext(MyCartContext);
  const [restaurant, setRestaurant] = useState();
  const [stats, setStats] = useState();
  const [filter, setFilter] = useState({
    year: "",
    month: "",
    quarter: "",
  });

  useEffect(() => {
    const fetchRestaurantByUserId = async () => {
      let endpoint = endpoints.getRestaurantByUserId;
      await Apis.get(`${endpoint}${id}`).then((res) => setRestaurant(res.data));
    };
    fetchRestaurantByUserId();
  }, []);

  const change = (evt, field) => {
    const value = evt.target.value;
    setFilter((current) => {
      return { ...current, [field]: value };
    });
  };

  useEffect(() => {
    const process = async () => {
      let endpoint = endpoints.restaurants;
      await Apis.get(`${endpoint}${restaurant?.id}/stats-food/`, {
        params: filter,
      })
        .then((res) => {
          const data = res.data?.map((item) => {
            return {
              id: item[0],
              name: item[1],
              totalAmount: item[2],
              quantity: item[3],
            };
          });
          setStats(data);
        })
        .catch((error) => console.log(error));
    };
    if (restaurant) {
      process();
    }
  }, [restaurant, filter]);

  return (
    <>
      <Container>
        <div>
          <h1 className="text-center mt-3">THỐNG KÊ DOANH THU</h1>
          <div className="">
            <Form className="d-flex justify-content-between">
              <Form.Group className="w-25">
                <Form.Label>Nhập năm:</Form.Label>
                <Form.Control
                  placeholder="Nhập năm"
                  onChange={(e) => change(e, "year")}
                />
              </Form.Group>

              <Form.Group className="w-25">
                <Form.Label>Chọn quý:</Form.Label>
                <Form.Select
                  onChange={(e) => change(e, "quarter")}
                  disabled={!filter.year || filter.month !== ""}
                >
                  <option value="">--- Chọn quý ---</option>
                  <option value="1">Quý 1</option>
                  <option value="2">Quý 2</option>
                  <option value="3">Quý 3</option>
                  <option value="4">Quý 4</option>
                </Form.Select>
              </Form.Group>

              <Form.Group className="w-25">
                <Form.Label>Chọn tháng:</Form.Label>
                <Form.Select
                  onChange={(e) => change(e, "month")}
                  disabled={!filter.year || filter.quarter !== ""}
                >
                  <option value="">--- Chọn tháng ---</option>
                  <option value="1">Tháng 1</option>
                  <option value="2">Tháng 2</option>
                  <option value="3">Tháng 3</option>
                  <option value="4">Tháng 4</option>
                  <option value="5">Tháng 5</option>
                  <option value="6">Tháng 6</option>
                  <option value="7">Tháng 7</option>
                  <option value="8">Tháng 8</option>
                  <option value="9">Tháng 9</option>
                  <option value="10">Tháng 10</option>
                  <option value="11">Tháng 11</option>
                  <option value="12">Tháng 12</option>
                </Form.Select>
              </Form.Group>
            </Form>
          </div>
          <ChartStast stats={stats} />
        </div>
      </Container>
    </>
  );
};

export default Stats;
