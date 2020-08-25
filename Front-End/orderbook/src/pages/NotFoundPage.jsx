import React, {Component} from "react";
import { Redirect} from "react-router-dom";
import {Alert} from "react-bootstrap";

class NotFoundPage extends Component{
    state = {
        redirect: false,
    }

    componentDidMount() {
        setTimeout(() => {
            this.setState({
                redirect: true,
            })
        }, 3000)
    }

    render() {
        if (this.state.redirect) {
            return (
                <Redirect to={'/'} />
            )
        }

        return (
            <Alert className="alert-danger">
                <Alert.Heading>Oh snap! You got an error!</Alert.Heading>
                <p>
                    Don't worry about a thing! You'll be redirected to the homepage in a couple of seconds!
                </p>
            </Alert>
        )
    }
}

export default NotFoundPage;