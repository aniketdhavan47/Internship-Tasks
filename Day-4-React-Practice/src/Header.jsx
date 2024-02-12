import { Component } from "react";
import "../public/Assets/header.css";
import { Link } from "react-router-dom";

class Header extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <div className="container">
        <div className="logo">
          <Link to={"/"} style={{ color: "black", textDecoration: "none" }}>
            {" "}
            <h3>Github</h3>
          </Link>
        </div>
        <div className="links">
          <ul>
            <Link to={"/"} style={{ color: "black", textDecoration: "none" }}>
              <li>Home</li>
            </Link>
            <Link
              to={"/about"}
              style={{ color: "black", textDecoration: "none" }}
            >
              <li>About</li>
            </Link>
            <Link
              to={"/about"}
              style={{ color: "black", textDecoration: "none" }}
            >
              <li>Profiles</li>
            </Link>
            <Link
              to={"/contact"}
              style={{ color: "black", textDecoration: "none" }}
            >
              <li>Contact</li>
            </Link>
          </ul>
        </div>
      </div>
    );
  }
}

export default Header;
