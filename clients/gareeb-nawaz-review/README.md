# Gareeb Nawaz — Google Review QR Kit

A standalone customer-facing review kit for **Gareeb Nawaz**, listed at **1, Khayam, Srinagar, Jammu and Kashmir 190006**.

## Included files

- `index.html` — mobile-friendly landing page with direct Google Review and Google Maps buttons.
- `qr.html` — QR generator with two scan destinations: direct Google Review form or the landing page. It can download a PNG QR.
- `poster.html` — print-friendly poster with the direct Google Review QR, browser print support, and QR PNG download.
- `README.md` — setup and handover notes.

## Google destination configured

- Place ID: `ChIJR2wnAd2P4TgR6OEeUQgyy2g`
- Direct review form: https://search.google.com/local/writereview?placeid=ChIJR2wnAd2P4TgR6OEeUQgyy2g
- Google Maps: https://www.google.com/maps/search/?api=1&query=Gareeb%20Nawaz%20Khayam%20Srinagar&query_place_id=ChIJR2wnAd2P4TgR6OEeUQgyy2g

## How the restaurant uses it

1. Confirm with the owner that the Google destination above opens the correct Gareeb Nawaz profile.
2. Open the published `poster.html` page and print it, or use `qr.html` to download a QR PNG.
3. For the simplest scan journey, use the **Direct Google Review form** QR. The customer's camera opens Google's review flow.
4. Test the QR from a second phone before printing multiple copies.
5. Place the poster or QR stand where customers can choose to scan it voluntarily.

## Publish and URL

The files are inside a separate folder so the existing Vexal-AI root application is not overwritten. If the repository's current GitHub Pages deployment publishes the repository root, these files will be available at:

- Landing page: https://addresscomin.github.io/Vexal-AI/clients/gareeb-nawaz-review/
- QR generator: https://addresscomin.github.io/Vexal-AI/clients/gareeb-nawaz-review/qr.html
- Print poster: https://addresscomin.github.io/Vexal-AI/clients/gareeb-nawaz-review/poster.html

GitHub Pages live status and the final public URLs still need to be checked in the repository's Pages settings. Do not tell the client that the website is live until it has been opened and tested on a phone.

## Analytics and limitations

- A direct-to-Google QR is the shortest route but does not provide scan counts.
- A landing-page QR can be connected to a privacy-conscious analytics provider later, but that requires an analytics property/ID and consent/configuration. No fake or local-only counter is presented as total scans.
- Google does not give this static page a reliable signal that a review was actually submitted.
- The page does not collect customer names, phone numbers, email addresses, or review text.
- Customers must be free to leave positive, neutral, or negative feedback. Do not gate, filter, or reward only positive reviews.

## Client quotation guidance

Suggested one-time service fee: **₹2,499** for setup, landing page, QR generator, print poster file, testing, and handover. Physical printing, acrylic table stands, custom domain, and paid third-party analytics are extra.
