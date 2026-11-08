# Gareeb Nawaz — Google Review QR

Customer-facing mini project for the Gareeb Nawaz listing at 1, Khayam, Srinagar, Jammu and Kashmir 190006.

## Files
- `index.html` — simple mobile-friendly landing page with Google Review and Google Maps buttons.
- `qr.html` — QR generator with two choices:
  - Direct Google Review form (recommended for the restaurant's printed table QR).
  - Landing page (use only after GitHub Pages is enabled and the page is live).

## Google destination
- Place ID: `ChIJR2wnAd2P4TgR6OEeUQgyy2g`
- Review URL: https://search.google.com/local/writereview?placeid=ChIJR2wnAd2P4TgR6OEeUQgyy2g
- Maps URL: https://www.google.com/maps/search/?api=1&query=Gareeb%20Nawaz%20Khayam%20Srinagar&query_place_id=ChIJR2wnAd2P4TgR6OEeUQgyy2g

## Publish
This folder was added without changing the repository's existing root files.

If GitHub Pages is enabled for the `main` branch with the repository root as the publishing source, the page URL should be:
https://addresscomin.github.io/Vexal-AI/clients/gareeb-nawaz-review/

Open `qr.html` at that published location to create a print-ready PNG QR. The direct-review option works independently of the landing page URL. The landing-page QR option should only be used after testing the published page.

## Testing checklist
1. Confirm with the restaurant owner that this is the correct Google Business Profile.
2. Open the direct review URL on a phone and confirm it opens the correct restaurant's review form.
3. Generate and download the direct-review QR from `qr.html`.
4. Scan the downloaded/printed QR from another phone.
5. If using the landing page, enable GitHub Pages and test its URL before printing that QR.

## Scope notes
- This setup sends customers to Google's own review flow. Customers can leave honest positive, neutral, or negative feedback.
- It does not filter reviews or promise a particular rating.
- Google does not provide a reliable confirmation to this static QR page that a review was actually submitted.
- The direct Google Review QR does not provide scan analytics. Counting scans requires routing scans through a hosted landing page and adding an analytics property/configuration.
- No personal customer data is collected by this static page.
- Printing, acrylic stands, custom domain, and any third-party analytics account are outside this code package.
