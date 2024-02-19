import { Suspense, lazy } from "react";
import ReactDOM  from "react-dom/client";
import { Outlet, RouterProvider, createBrowserRouter ,useParams} from "react-router-dom";
import Header from "./Components/Header";
import Error from "./Components/Error";
import Footer from "./Components/Footer";
import Body from "./Components/Body";

import Card from "./Components/card";
import ShowProfile from "./Components/ShowProfile";
import Shimmer from "./Components/Shimmer";
import Blogs from "./Components/Blogs";

const About = lazy(() => import('./Components/About'));

const App=()=>{
    return(
        <>
         <Header/>
         <Outlet/>
         <Footer/>
        </>
    )

}

// Code Splitting /Dynamic Import /Lazy Loading /on demand loading /Dynamic Bundling

const appRouter=createBrowserRouter([
    {
        path:"/",
        element:<App/>,
        errorElement:<Error/>,
        children:[
            {
                path:'/',
                element:<Body/>
            },
            {
                path:"/profile/:username",
                element:<ShowProfile/>,
                
            },{
                path:'/about',
                element:(
                    <Suspense fallback={<Shimmer/>}>
                        <About/>

                    </Suspense>
                )
            },
            {
                path:'/blogs',
                element:<Blogs/>
            }
        ]
    }
])

const root=ReactDOM.createRoot(document.getElementById("root"))
root.render(<RouterProvider router={appRouter}/>)