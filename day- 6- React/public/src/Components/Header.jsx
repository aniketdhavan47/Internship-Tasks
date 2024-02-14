import '../assets/styles/header.css'
import { Link } from 'react-router-dom';
const Header=()=>{
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
                    <Link className='mx-5'>
                        <li>About</li>
                    </Link>
                    <Link className='mx-5'>
                        <li>Blogs</li>
                    </Link>
                    <Link className='mx-5'>
                        <li>Contact</li>
                    </Link>
                    <Link className='mx-5'>
                        <li>Log In</li>
                    </Link>
                </ul>
            </div>
           </div>
        </>
    )
}

export default Header;