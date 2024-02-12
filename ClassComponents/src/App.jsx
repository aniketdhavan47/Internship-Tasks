import React from "react";
import ReactDOM from "react-dom/client";
import About from "./About";
import Header from "./Header";
import Profile from "./profile";
import Footer from "./Footer";
import Contact from "./Contact";
import { createBrowserRouter, RouterProvider, Outlet } from "react-router-dom";
// import { createBrowserRouter, RouterProvider } from "react-router-dom";
export default function App() {
  return (
    <div className="App">
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
}

const appRouter = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <Error />,
    children: [
      {
        path: "/",
        element: <About />,
      },
      {
        path: "/about",
        element: <About />,
        children: [
          {
            path: "profile/:id",
            element: <Error />,
          },
        ],
      },
      {
        path: "/contact",
        element: <Contact />,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<RouterProvider router={appRouter} />);
