import { Component } from "react";
import "../public/Assets/footer.css";

class Footer extends Component {
  render() {
    return (
      <div className="footer">
        <div className="logo">
          <h1>GitHub</h1>
        </div>
        <div className="links">
          <ul>
            <li>Instagram</li>
            <li>LinkedIn</li>
            <li>Github</li>
            <li>Blogs</li>
          </ul>
        </div>
        <div className="copyright">
          © 2016 - 2024 Github.com <br />- All Rights Reserved.
          <br />
          Last Updated : 02/12/2024 14:45:52
        </div>
      </div>
    );
  }
}
export default Footer;
