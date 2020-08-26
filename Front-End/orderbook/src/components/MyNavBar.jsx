import React from "react";
import { Nav, Navbar, Button } from "react-bootstrap";
import '../styles/NavBar.css'

function NavBar() {
    return(
        <Navbar className="MyNav justify-content-center" bg="light" expand="lg">
            <Navbar.Brand href="/404"></Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="m-auto">
                    <Nav.Link className="link" href="/">Home</Nav.Link>
                    <Nav.Link className="link" href="/allTrades">All Trades</Nav.Link>
                </Nav>
                    <Button variant="outline-success">Login</Button>
            </Navbar.Collapse>
        </Navbar>
    )
}

export default NavBar;