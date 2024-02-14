import ReactDOM  from "react-dom/client";
import { Outlet, RouterProvider, createBrowserRouter ,useParams} from "react-router-dom";
import Header from "./Components/Header";
import Footer from "./Components/Footer";
import Body from "./Components/Body";
import About from "./Components/About";
import Card from "./Components/card";
import ShowProfile from "./Components/ShowProfile";

const App=()=>{
    return(
        <>
         <Header/>
         <Outlet/>
         <Footer/>
        </>
    )

}

const appRouter=createBrowserRouter([
    {
        path:"/",
        element:<App/>,
        children:[
            {
                path:'/',
                element:<Body/>
            },
            {
                path:"/profile/:username",
                element:<ShowProfile/>,
                
            }
        ]
    }
])

const root=ReactDOM.createRoot(document.getElementById("root"))
root.render(<RouterProvider router={appRouter}/>)