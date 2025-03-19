import {Link} from "react-router-dom";

function NavBar() {
    return <nav className="nav-bar">
        <Link to="/">Home</Link>
        <Link to="/favorites">Favorites</Link>
        <Link to="/api-call">Api Call</Link>
    </nav>
}

export default NavBar