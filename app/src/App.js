import React, { Component } from 'react'
import axios from 'axios'
import logo from './logo.svg'
import './App.css'

const api = 'http://localhost:8080/api/'
const headers = {
  'content-type': 'application/json', 'creds':'user'
}

class App extends Component {
  componentDidMount() {
    // WORKING!
    axios
      .get(`${api}/account/v1/users`)
      .then(response => {
        console.log(response.data)
      })
      .catch(function(error) {
        console.log(error)
      })

  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit
          <code>src/App.js</code>
          and save to reload.
        </p>
      </div>
    )
  }
}

export default App
