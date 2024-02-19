import useOnline from '../Hooks/useOnline';
import '../assets/styles/header.css'
import { Link } from 'react-router-dom';
const Header=()=>{
    const isOnline=useOnline();
    return(
        <>
       
           <div className="flex justify-between h-39 text-white bg-black p-5 sticky top-0">
            <div className="logo font-bold text-3xl">
                <Link to={"/"}>Github</Link>
            </div>
            <div className="links ">
                <ul className='flex '>
                    <Link className='mx-5'>
                        <li>Home</li>
                    </Link>
                    <Link className='mx-5' to={"/about"}>
                        <li>About</li>
                    </Link>
                    <Link className='mx-5' to={'/blogs'}>
                        <li>Blogs</li>
                    </Link>
                    <Link className='mx-5'>
                        <li>Contact</li>
                    </Link>
                    <Link className='mx-5'>
                        <li>Log In</li>
                    </Link>
                    <li>
                        {isOnline?"🟢":"🔴"}
                    </li>
                </ul>
            </div>
           </div>
        </>
    )
}

export default Header;