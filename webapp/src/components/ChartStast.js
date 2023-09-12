import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

function ChartStast({ stats }) {
  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "Thống kê doanh thu sản phẩm",
      },
    },
    scales: {
      y: {
        beginAtZero: true,
        position: "left",
      },
      y1: {
        beginAtZero: true,
        position: "right",
        grid: {
          drawOnChartArea: false,
        },
      },
    },
  };

  const labels = stats?.map((item) => item.name);

  const data = {
    labels,
    datasets: [
      {
        label: "Tổng tiền",
        data: stats?.map((item) => item.totalAmount),
        backgroundColor: "rgba(255, 99, 132, 0.5)",
        yAxisID: 'y',
      },
      {
        label: "Số lượng",
        data: stats?.map((item) => item.quantity),
        backgroundColor: "rgba(53, 162, 235, 0.5)",
        yAxisID: 'y1',
      },
    ],
  };

  return (
    <Bar
      style={{
        maxHeight: "500px",
      }}
      options={options}
      data={data}
    />
  );
}

export default ChartStast;
