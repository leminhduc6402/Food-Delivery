import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faStar,
  faStarHalfStroke
} from "@fortawesome/free-solid-svg-icons";

const StarRating = ({ averageRating }) => {
  const stars = [];
  
  for (let i = 1; i <= 5; i++) {
    if (i <= averageRating) {
      stars.push(<FontAwesomeIcon key={i} icon={faStar} style={{ color: "#ee4d2d" }} />);
    } else if (i > averageRating && i < averageRating + 0.5) {
      stars.push(<FontAwesomeIcon key={i} icon={faStarHalfStroke} style={{ color: "#ee4d2d" }} />);
    } else {
      stars.push(<FontAwesomeIcon key={i} icon={faStar} />);
    }
  }

  return (
    <div className="d-flex my-1">
      <ul className="d-flex p-0 my-0">
        {stars.map((star, index) => (
          <li key={index} style={styles.star}>
            {star}
          </li>
        ))}
      </ul>
    </div>
  );
};

const styles = {
  star: {
    display: "flex",
    listStyle: "none",
    // color: "#ffc107",
    marginRight: 5,
    alignItems: "center",
  },
};

export default StarRating;