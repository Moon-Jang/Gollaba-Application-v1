import * as React from "react"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import Box from "@mui/material/Box"
import Button from "@mui/material/Button"
import Link from "next/link"

export default function TempHome() {
    return (
        <Container maxWidth="sm">
            <Box sx={{ my: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Next.js example
                </Typography>
                <a
                    href={
                        "https://localhost:8080/oauth2/authorize/facebook?redirect_uri=http://localhost:3000/temp/oauth-callback"
                    }
                >
                    facebook login - localhost
                </a>
                <p></p>
                <a
                    href={
                        "https://dev.api.gollaba.net/oauth2/authorize/facebook?redirect_uri=http://localhost:3000/temp/oauth-callback"
                    }
                >
                    facebook login - dev.api.gollaba.net
                </a>
            </Box>
        </Container>
    )
}
