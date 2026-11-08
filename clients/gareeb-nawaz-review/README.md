# Gareeb Nawaz Restaurant Gulmarg — Google Review QR System

A single-purpose QR review kit for Gareeb Nawaz Restaurant Gulmarg. It does not include ordering, booking, customer accounts, payments, maps tools, or other business systems.

## Files
- `index.html` — simple customer review page with one Google Review button.
- `qr.html` — generates and downloads the direct Google Review QR as PNG.
- `poster.html` — print-ready review poster with the same direct-review QR, print and PNG-download controls.

## Business details
- Business: Gareeb Nawaz Restaurant Gulmarg
- Address: Main Market, Gulmarg Road, near J&K Bank, Gulmarg, Jammu and Kashmir 193403
- Phone: +91 70064 60130
- Google Place ID currently configured: `ChIJrwguek2v4TgRHRmwAN6NjwM`
- Review URL: https://search.google.com/local/writereview?placeid=ChIJrwguek2v4TgRHRmwAN6NjwM
- Maps listing: https://www.google.com/maps/search/?api=1&query=Gareeb%20Nawaz%20Restaurant%20Gulmarg&query_place_id=ChIJrwguek2v4TgRHRmwAN6NjwM

## Before printing or handover
1. Open the direct review URL on a phone and confirm the Google profile name and address match this restaurant.
2. Open `qr.html`, download the PNG, and scan it using a second phone.
3. Confirm the scan opens the correct Google review profile.
4. Print one sample and test scan distance and print clarity.

**Important:** the business phone number and address match the public listing, but the configured Place ID/review destination still requires a live phone check. Do not mass-print or hand over as verified until that test passes.

QR generation loads a browser QR library from a CDN, so internet access is required when opening the generator/poster. This static kit does not track QR scans or verify whether a Google review was submitted. Customers must be free to leave honest positive, neutral, or negative feedback; no review gating or incentives are used.
